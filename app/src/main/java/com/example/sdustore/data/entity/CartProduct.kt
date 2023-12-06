package com.example.sdustore.data.entity

data class CartProduct(
    val product: Product,
    val quantity: Int,
    val selectedSize: String? = null,
){
    constructor(): this(Product(),1,null)
}
