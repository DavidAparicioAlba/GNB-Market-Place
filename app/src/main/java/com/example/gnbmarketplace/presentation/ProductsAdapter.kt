package com.example.gnbmarketplace.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gnbmarketplace.R
import com.example.gnbmarketplace.domain.models.product.ProductEntity
import com.squareup.picasso.Picasso

class ProductsAdapter(var products: List<ProductEntity>,
                        var imgs: ArrayList<String>,
                        var onClick: (ProductEntity) -> Unit): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(products[position], position)
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tvProductSku: TextView =    itemView.findViewById(R.id.tv_product_title)
        private val ivProduct: ImageView = itemView.findViewById(R.id.iv_product)

        fun onBind(product: ProductEntity, position: Int) {
            tvProductSku.text = product.sku
            Picasso.get().load(imgs[position]).into(ivProduct)
            ivProduct.setOnClickListener {
                onClick(product)
            }
        }
    }
}