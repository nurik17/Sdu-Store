package com.example.sdustore.ui.category.productDetail

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
    private var selectedSize: String? = null

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
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedSize))
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addToCart.collectLatest {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {

                        }

                        is Resource.Success -> {
                            binding.btnAddToBasket.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.color_green
                                )
                            )
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

    private fun changeBtnColor() {
        binding.btnAddToBasket.setSafeOnClickListener {
            binding.btnAddToBasket.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_green
                )
            )
            binding.btnAddToBasket.text = getString(R.string.added_to_basket)
        }
    }

    private fun setUpBackButton() {
        binding.icArrowLeft.setSafeOnClickListener {
            findNavController().popBackStack()
        }
    }

//    private fun shareProduct(){
//
//        binding.icShare.setSafeOnClickListener {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT,product.id)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            startActivity(shareIntent)
//        }
//    }
}