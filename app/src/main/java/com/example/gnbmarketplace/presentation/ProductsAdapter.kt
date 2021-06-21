package com.example.gnbmarketplace.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gnbmarketplace.R
import com.example.gnbmarketplace.domain.models.product.ProductEntity

class ProductsAdapter(var products: List<ProductEntity>): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_product, parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(products[position], position)
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvProductSku: TextView =    itemView.findViewById(R.id.tv_product_title)

        fun onBind(product: ProductEntity, position: Int) {
            tvProductSku.text = product.sku
        }
    }
}