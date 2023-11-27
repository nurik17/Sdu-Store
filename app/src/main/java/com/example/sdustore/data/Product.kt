package com.example.sdustore.data

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val offerPercentage: Double? = null,
    //val description: String? = null,
    //val colors: List<Int>? = null,
    val sizes:List<String>? = null,
    val images: List<String>
){
    constructor():this("0","","",0, images = emptyList())
}