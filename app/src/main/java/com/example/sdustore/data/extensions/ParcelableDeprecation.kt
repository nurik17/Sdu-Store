package com.example.sdustore.data.extensions

import android.os.Build
import android.os.Parcelable
import androidx.fragment.app.Fragment

inline fun <reified T : Parcelable> Fragment.getParcelableArgumentCompat(key: String): T? =
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
            arguments?.getParcelable(key)

        else ->
            @Suppress("DEPRECATION")
            arguments?.getParcelable(key) as? T
    }