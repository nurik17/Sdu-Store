package com.example.sdustore.ui.category

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.extensions.dp
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.databinding.FragmentProductdetailsBinding
import com.example.sdustore.ui.category.adapter.ProductSizesAdapter
import com.example.sdustore.ui.category.adapter.ScrollImageAdapter

class ProductDetailsFragment :
    BaseFragment<FragmentProductdetailsBinding>(FragmentProductdetailsBinding::inflate) {

    private val args by navArgs<ProductDetailsFragmentArgs>()

    private val scrollImageAdapter by lazy {
        ScrollImageAdapter()
    }
    private val sizesAdapter by lazy {
        ProductSizesAdapter()
    }

    override fun onBindView() {
        super.onBindView()
        setUpScrollRv()
        setUpSizesRv()
        dataOfProducts()
        changeBtnColor()
    }

    @SuppressLint("SetTextI18n")
    private fun dataOfProducts(){
        val product = args.product

        val price = product.price.toString()
        val spannableString = SpannableString(price)
        spannableString.setSpan(StrikethroughSpan(),0,price.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.apply {
            productCategoryName.text = product.category
            productName.text = product.name
            if(product.offerPercentage!! > 0){
                productSalePrice.text = spannableString
                productPrice.text = (product.price-(product.price * product.offerPercentage)).toInt().toString() + "₸"
            }else {
                productSalePrice.visibility = View.GONE
                (productPrice.layoutParams as ViewGroup.MarginLayoutParams).marginStart = 0.dp
            }
        }
        scrollImageAdapter.setData(product.images)
        product.sizes?.let { sizesAdapter.setData(it) }

    }

    private fun setUpScrollRv() {
        binding.apply {
            viewPagerImages.adapter = scrollImageAdapter
            dotsIndicator.attachTo(viewPagerImages)
        }
    }
    private fun setUpSizesRv(){
        binding.rvSizes.adapter = sizesAdapter
        binding.rvSizes.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun changeBtnColor(){
        binding.btnAddToBasket.setSafeOnClickListener {
            binding.btnAddToBasket.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.color_green))
            binding.btnAddToBasket.text = getString(R.string.added_to_basket)
        }
    }
}