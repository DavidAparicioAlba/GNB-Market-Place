package com.example.gnbmarketplace.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gnbmarketplace.R
import com.example.gnbmarketplace.core.ScreenState
import com.example.gnbmarketplace.data.cache.SharedPreferencesManager
import com.example.gnbmarketplace.domain.models.conversion.ConversionEntity
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.example.gnbmarketplace.presentation.ProductDetailsFragment.Companion.ARG_TAG
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModels()
    private var mAdapter: ProductsAdapter? = null
    private var imgList = arrayListOf<String>()
    private lateinit var product: ProductEntity
    private lateinit var products: ArrayList<ProductEntity>
    private var productDetailDialog: ProductDetailsFragment? = null
    private lateinit var conversions: ArrayList<ConversionEntity>
    var path = arrayListOf<String>()
    var checkedPath = arrayListOf<String>()
    var recursiveIndex = 0
    var remainingConversions = arrayListOf<ConversionEntity>()

    @Inject
    lateinit var sharedPrefs: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initImages()
        initStates()
    }

    private fun initImages() {
        Picasso.get().load("https://media-exp1.licdn.com/dms/image/C560BAQErAcojsF4kWw/company-logo_200_200/0/1519884883678?e=2159024400&v=beta&t=d3LF_PyYk1qYteez1zdB7RpHmKlZ5pYmHag_TqEAJtg").into(iv_header)

    }

    private fun onClickItem(productEntity: ProductEntity){
        product = productEntity
        //mViewModel.obtainConversions()
        handleConversions(conversions)
    }


    private fun initStates(){
        mViewModel.state.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(state: ScreenState<MainActivityScreenState>?){
        when(state) {
            is ScreenState.Loading -> {
                //pb_login.visibility = View.VISIBLE
            }
            is ScreenState.Render -> {
                //pb_login.visibility = View.GONE
                renderScreenState(state.renderState)
            }
        }
    }

    private fun renderScreenState(screenState: MainActivityScreenState) {
        when(screenState) {
            is MainActivityScreenState.Init -> {
                mViewModel.obtainProducts()
            }
            is MainActivityScreenState.ErrorShowProducts ->{

                Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show()
            }
            is MainActivityScreenState.ShowProducts ->{

                products = screenState.productsListEntity as ArrayList<ProductEntity>
                for (i in screenState.productsListEntity.indices)
                    imgList.add("https://picsum.photos/500/300?random=$i")
                updateAdapter(screenState.productsListEntity)
            }
            is MainActivityScreenState.ShowConversions -> {
                //TODO: show Dialog
                conversions = screenState.conversionsListEntity as ArrayList<ConversionEntity>

            }
            else -> {}
        }
    }

    private fun handleConversions(conversionsList: List<ConversionEntity>) {

        conversions = conversionsList as ArrayList<ConversionEntity>

        completeConversions(conversions)

        var sumCost = 0f

        val copyProducts = products

        copyProducts.map {  p ->
            if (!p.currency?.equals("eur", true)!!) {
                p.amount = ((p.amount?.toFloat() ?: 0f) * (conversions.find { it.from.equals(p.currency) }?.rate?.toFloat() ?: 0f)).toString()
                p.currency = "eur"
            }
        }

        val filteredProducts = copyProducts.filter { it == product }
        filteredProducts.forEach {
            sumCost += (it.amount?.toFloat() ?: 0f)
        }

        productDetailDialog = product.sku?.let {
            ProductDetailsFragment.newInstance(
                    filteredProducts.size,
                    it,
                    sumCost,
                    imgList[products.indexOf(product)]
            )
        }
        supportFragmentManager.let {
            productDetailDialog?.show(it, ARG_TAG)
        }
    }

    private fun updateAdapter(products: List<ProductEntity>) {
        mAdapter = ProductsAdapter(products,
            imgList,
            ::onClickItem)
        rcv_products.adapter = mAdapter
        rcv_products.layoutManager = LinearLayoutManager(this)
        mViewModel.obtainConversions()
    }
    //TODO: move to VM
    private fun completeConversions(conversions:ArrayList<ConversionEntity>) {
        //get currencies list
        val currenciesList = arrayListOf<String>()
        conversions.map {
            if (!currenciesList.contains(it.from)){
                it.from?.let { it1 -> currenciesList.add(it1) }
            }
        }
        //n*(n+1)/2 in this case (n-1)*n for each relation there are 2 directions
        val posibilities = (currenciesList.size-1)*currenciesList.size
        // get remaining conversions
        for (i in 0 until currenciesList.size) {
            for (j in 0 until currenciesList.size) {
                if (i != j && !existConversion(conversions, currenciesList[i], currenciesList[j]) && conversions.size < posibilities) {
                    remainingConversions.add(ConversionEntity(currenciesList[i], currenciesList[j], "0"))
                }
            }
        }
        //until here works
        // find path for remaining conversion rate
        val pathCurrencies = arrayListOf<ArrayList<String>>()

        for (i in 0 until remainingConversions.size) {
            pathCurrencies.add(remainingConversions[i].to?.let { remainingConversions[i].from?.let { it1 -> findPath(conversions, it1, it) } }!!)
            path = arrayListOf()
        }
        for (i in 0 until pathCurrencies.size) {
            var cost = 0f
            if (pathCurrencies[i].size >= 2)
                cost = conversions.find { it.from!!.equals(pathCurrencies[i][0]) && it.to!!.equals(pathCurrencies[i][1]) }?.rate!!.toFloat()
            for (j in 1..pathCurrencies[i].size-2) {
                cost *= conversions.find { it.from!!.equals(pathCurrencies[i][j]) && it.to!!.equals(pathCurrencies[i][j+1]) }?.rate!!.toFloat()
            }
            remainingConversions[i].rate = cost.toString()
            if (conversions.size < posibilities)
                conversions.add(remainingConversions[i])
        }
    }

    //TODO: move to VM
    private fun findPath(convsList: ArrayList<ConversionEntity>, from: String, to: String): ArrayList<String> {
        checkedPath.add(from)
        var filterFrom = convsList

        filterFrom = filterFrom.filter {
            it.from == from
        } as ArrayList

        //filtered correctly
        filterFrom[0].let {
            return if (existConversion(filterFrom, it.from!!, to)) {
                path = checkedPath
                path.add(to)
                checkedPath = arrayListOf()
                recursiveIndex = 0
                path
            } else {
                //find next step in path
                if (filterFrom.isNotEmpty()) {
                    //update pair
                    var newFilter = conversions

                    newFilter = newFilter.filter { it2 ->
                        it2.from == it.to
                    } as ArrayList

                    findPath(newFilter, it.to!!, to)
                } else {
                    //path not found, reset checked list but 1
                    if (checkedPath.size == convsList.size-1 && path[path.size-1] != to) {
                        recursiveIndex++
                        for (j in recursiveIndex until checkedPath.size) {
                            checkedPath.removeAt(recursiveIndex+1)
                        }
                    }
                    findPath(remainingConversions, it.to!!, to)
                }
            }
        }
    }

    //TODO: move to VM
    private fun existConversion(conversionEntities: ArrayList<ConversionEntity>, from: String, to: String): Boolean {
        val match = conversionEntities.filter { it.from == from && it.to == to }.size
        return match != 0
    }
}