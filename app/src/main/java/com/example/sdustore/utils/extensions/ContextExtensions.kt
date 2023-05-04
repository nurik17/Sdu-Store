package com.example.sdustore.utils.extensions

import android.content.Context
import android.content.res.Configuration

fun Context.isNightMode(): Boolean {
    val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
}