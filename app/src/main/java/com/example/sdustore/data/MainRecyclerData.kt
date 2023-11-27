package com.example.sdustore.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainRecyclerData(
    val textTitle: String? = null,
    val textSubTitle: String? = null,
    var imageUrl: String? = null,
):Parcelable{
    constructor():this("","","")
}