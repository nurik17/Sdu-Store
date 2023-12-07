package com.example.sdustore.ui.category.productDetail

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sdustore.R
import com.example.sdustore.base.BaseFragment
import com.example.sdustore.data.entity.CartProduct
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.data.extensions.dp
import com.example.sdustore.data.extensions.setSafeOnClickListener
import com.example.sdustore.databinding.FragmentProductdetailsBinding
import com.example.sdustore.ui.category.adapter.ProductSizesAdapter
import com.example.sdustore.ui.category.adapter.ScrollImageAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragment<FragmentProductdetailsBinding>(FragmentProductdetailsBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val scrollImageAdapter by lazy {
        ScrollImageAdapter()
    }
    private val sizesAdapter by lazy {
        ProductSizesAdapter()
    }
    private var selectedSize: String = ""

    override fun onBindView() {
        super.onBindView()
        val product = args.product

        setUpScrollRv()
        setUpSizesRv()
        dataOfProducts()
        setUpBackButton()


        sizesAdapter.onItemClick = {
            selectedSize = it
        }
        binding.btnAddToBasket.setSafeOnClickListener {
            if(selectedSize.isNotBlank()) {
                viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedSize))
            }else{
                Snackbar.make(requireView(),"Please choose a size of ${product.name}",Snackbar.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addToCart.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            animateBtn(binding.btnAddToBasket)
                        }

                        is Resource.Error -> {
                            Snackbar.make(requireView(),it.message.toString(),Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Success -> {
                            binding.btnAddToBasket.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.color_green
                                )
                            )
                            Snackbar.make(requireView(),"Product successfully added to basket",Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun dataOfProducts() {
        val product = args.product
        val price = product.price.toString()
        val spannableString = SpannableString(price)
        spannableString.setSpan(
            StrikethroughSpan(),
            0,
            price.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.apply {
            productCategoryName.text = product.category
            productName.text = product.name
            if (product.offerPercentage!! > 0) {
                productSalePrice.text = spannableString
                productPrice.text =
                    (product.price - (product.price * product.offerPercentage)).toInt()
                        .toString() + "₸"
            } else {
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

    private fun setUpSizesRv() {
        binding.rvSizes.adapter = sizesAdapter
        binding.rvSizes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun animateBtn(button: View) {
        val scale = ObjectAnimator.ofPropertyValuesHolder(
            button,
            PropertyValuesHolder.ofFloat(View.SCALE_X,1f,1.5f,1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y,1f,1.5f,1f),
        )
        scale.duration = 1000
        scale.start()
    }

    private fun setUpBackButton() {
        binding.icArrowLeft.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }
}