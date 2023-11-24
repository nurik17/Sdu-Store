package com.example.sdustore.data

data class MainRecyclerData(
    val textTitle: String? = null,
    val textSubTitle: String? = null,
    var imageUrl: String? = null,
){
    constructor():this("","","")
}