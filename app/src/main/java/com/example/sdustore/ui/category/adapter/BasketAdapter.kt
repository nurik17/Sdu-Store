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
import com.example.sdustore.data.entity.CartProduct
import com.example.sdustore.data.extensions.dp
import com.example.sdustore.databinding.BasketItemBinding

class BasketAdapter() : ListAdapter<CartProduct, BasketAdapter.BasketViewHolder>(DiffUtilCallBack()) {

    fun setData(productList: List<CartProduct>) {
        submitList(productList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BasketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BasketItemBinding.inflate(inflater, parent, false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)


        holder.itemView.setOnClickListener {
            onProductClick?.invoke(item)
        }
        holder.binding.icMinus.setOnClickListener {
            onMinusClick?.invoke(item)
        }
        holder.binding.icPlus.setOnClickListener {
            onPlusClick?.invoke(item)
        }
    }

    var onProductClick: ((CartProduct)-> Unit)? = null
    var onPlusClick: ((CartProduct)-> Unit)? = null
    var onMinusClick: ((CartProduct)-> Unit)? = null

    class BasketViewHolder(val binding: BasketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CartProduct) {

            val price = item.product.price.toString()
            val spannableString = SpannableString(price)
            spannableString.setSpan(StrikethroughSpan(),0,price.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.apply {
                productCategoryName.text = item.product.category
                productName.text = item.product.name
                quantityProduct.text = item.quantity.toString()
                sizeName.text = item.selectedSize?:"".also {  }
                if(item.product.offerPercentage!! > 0){
                    productOfferPercentage.text = ("-" +(item.product.offerPercentage * 100) + "%")
                    productSalePrice.text = spannableString
                    productSalePrice.text = (item.product.price-(item.product.price * item.product.offerPercentage)).toInt().toString()
                }else{
                    productOfferPercentage.visibility = View.INVISIBLE
                    productSalePrice.visibility = View.GONE
                    (productSalePrice.layoutParams as ViewGroup.MarginLayoutParams).marginStart = 0.dp
                }
                Glide.with(productImage)
                    .load(item.product.images[0])
                    .into(productImage)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }
}

