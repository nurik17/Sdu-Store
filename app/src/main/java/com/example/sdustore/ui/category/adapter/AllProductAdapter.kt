package com.example.sdustore.ui.category.adapter

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sdustore.data.entity.Product
import com.example.sdustore.databinding.ProductAllItemBinding
import com.example.sdustore.data.extensions.dp

class AllProductAdapter(
    private val onClick: (Product) -> Unit
) : ListAdapter<Product, AllProductAdapter.ProductViewHolder>(DiffUtilCallBack()) {

    fun setData(productList: List<Product>) {
        submitList(productList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductAllItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    class ProductViewHolder(private val binding: ProductAllItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Product, onClick: (Product) -> Unit) {

            val price = item.price.toString()
            val spannableString = SpannableString(price)
            spannableString.setSpan(StrikethroughSpan(),0,price.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.apply {
                productCategoryName.text = item.category
                productName.text = item.name
                if(item.offerPercentage!! > 0){
                    productOfferPercentage.text = ("-" +(item.offerPercentage * 100) + "%")
                    productPriceOffer.text = spannableString
                    productPrice.text = (item.price-(item.price * item.offerPercentage)).toInt().toString()
                }else{
                    productOfferPercentage.visibility = View.INVISIBLE
                    productPriceOffer.visibility = View.GONE
                    (productPrice.layoutParams as ViewGroup.MarginLayoutParams).marginStart = 0.dp
                }
                Glide.with(productImage)
                    .load(item.images[0])
                    .into(productImage)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

