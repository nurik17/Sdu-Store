package com.example.sdustore.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val offerPercentage: Double? = null,
    //val description: String? = null,
    //val colors: List<Int>? = null,
    val sizes:List<String>? = null,
    val images: List<String>
): Parcelable{
    constructor():this("0","","",0, images = emptyList())
}