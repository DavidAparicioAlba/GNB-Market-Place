package com.example.gnbmarketplace.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gnbmarketplace.R
import com.example.gnbmarketplace.core.ScreenState
import com.example.gnbmarketplace.data.cache.SharedPreferencesManager
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModels()
    private var mAdapter: ProductsAdapter? = null

    @Inject
    lateinit var sharedPrefs: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        initStates()
    }

    private fun initListeners(){
        //TODO: listeners for rcv only

    }


    private fun initStates(){
        mViewModel.state.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(state: ScreenState<MainActivityScreenState>?){
        when(state) {
            is ScreenState.Loading -> {
                //pb_login.visibility = View.VISIBLE
                toggleControls(false)
            }
            is ScreenState.Render -> {
                toggleControls(true)
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
                updateAdapter(screenState.productsListEntity)
            }


        }
    }

    private fun updateAdapter(products: List<ProductEntity>) {
        mAdapter = ProductsAdapter(products)
        rcv_products.adapter = mAdapter
        rcv_products.layoutManager = LinearLayoutManager(this)
    }

    private fun toggleControls(enabled:Boolean) {

    }


}