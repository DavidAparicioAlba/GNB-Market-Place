package com.example.gnbmarketplace.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.gnbmarketplace.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_details_dialog.*

class ProductDetailsFragment : androidx.fragment.app.DialogFragment() {

    private lateinit var productImage: String
    private lateinit var productName: String
    private var productAmount: Int = 0
    private var productTotalCost: Float = 0f

    companion object {

        private const val ARG_AMOUNT = "amount"
        private const val ARG_PROD_NAME = "name"
        private const val ARG_COST = "mandatory"
        private const val ARG_IMAGE = "image"
        const val ARG_TAG = "productDetailFragment"


        fun newInstance(amount: Int, productName: String, totalCost: Float, img: String): ProductDetailsFragment {
            val productsDetailFragment = ProductDetailsFragment()
            val bundle = Bundle()

            bundle.putInt(ARG_AMOUNT, amount)
            bundle.putString(ARG_PROD_NAME, productName)
            bundle.putFloat(ARG_COST, totalCost)
            bundle.putString(ARG_IMAGE, img)

            productsDetailFragment.arguments = bundle

            return productsDetailFragment

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        return inflater.inflate(R.layout.product_details_dialog, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productName = it.getString(ARG_PROD_NAME).toString()
            productTotalCost = it.getFloat(ARG_COST)
            productImage = it.getString(ARG_IMAGE).toString()
            productAmount = it.getInt(ARG_AMOUNT)
        }

        tv_product_name.text = productName

        tv_product_amount.text = getString(R.string.tv_product_amount_text)+": "+productAmount

        tv_product_cost.text = getString(R.string.tv_product_total_price_text)+": "+"%.2f".format(productTotalCost)+"€"

        Picasso.get().load(productImage).into(iv_product_dialog)

        btn_buy_product.text = getString(R.string.btn_product_buy_text)

        btn_buy_product.setOnClickListener {
            Toast.makeText(context, getString(R.string.succesfull_buy_text), Toast.LENGTH_LONG).show()
            dismiss()
        }
        ib_close_product_dilog.setOnClickListener{
            dismiss()
        }

    }


}