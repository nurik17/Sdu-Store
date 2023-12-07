package com.example.sdustore.ui.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.entity.CartProduct
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.firebase.FireBaseCommon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val fireBaseCommon: FireBaseCommon
): ViewModel() {

    private val _cartProduct = MutableStateFlow<Resource<List<CartProduct>>>(Resource.UnSpecified())
    val cartProduct = _cartProduct.asStateFlow()

    private var cartProductDocuments = emptyList<DocumentSnapshot>()

    init{
        getCartProducts()
    }

    private fun getCartProducts(){
        viewModelScope.launch {_cartProduct.emit(Resource.Loading())}
        fireStore.collection("user")
            .document(auth.uid!!)
            .collection("cart")
            .addSnapshotListener { value, error ->
                if(error != null || value == null){
                    viewModelScope.launch { _cartProduct.emit(Resource.Error(error?.message.toString())) }
                }else{
                    cartProductDocuments = value.documents
                    val cartProduct = value.toObjects(CartProduct::class.java)
                    viewModelScope.launch { _cartProduct.emit(Resource.Success(cartProduct)) }
                }
            }
    }

    fun changeQuality(
        cartProducts: CartProduct,
        quantityChange: FireBaseCommon.QuantityChange
    ) {
        val index = cartProduct.value.data?.indexOf(cartProducts)
        if (index != null && index != -1) {
            val documentId = cartProductDocuments[index].id
            when(quantityChange){
                FireBaseCommon.QuantityChange.INCREASE->{
                    increaseQuantity(documentId)
                }
                FireBaseCommon.QuantityChange.DECREASE->{
                    decreaseQuantity(documentId)
                }
            }
        }
    }

    private fun decreaseQuantity(documentId: String) {
        fireBaseCommon.decreaseQuantity(documentId){result,e->
            if(e != null){
                viewModelScope.launch { _cartProduct.emit(Resource.Error(e.message.toString()))}
            }
        }
    }

    private fun increaseQuantity(documentId: String) {
        fireBaseCommon.increaseQuantity(documentId) { result, e ->
            if (e != null) {
                viewModelScope.launch { _cartProduct.emit(Resource.Error(e.message.toString())) }
            }
        }
    }
}