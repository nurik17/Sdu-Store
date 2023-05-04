package com.example.sdustore.utils.extensions

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun Window.addPadding(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            /* val statusBarTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top */
            val navigationBar = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.setPadding(
                0,
                0,
                0,
                if (imeHeight == 0) {
                    navigationBar
                } else {
                    imeHeight - navigationBar
                }
            )
            return@setOnApplyWindowInsetsListener insets
        }
    } else {
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}

fun Window.setStatusBarContentColor(
    isLightStatusBar: Boolean
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (insetsController != null) {
            if (isLightStatusBar) {
                insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                insetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }
    } else {
        var systemUiVisibilityFlags = decorView.systemUiVisibility
        systemUiVisibilityFlags = if (isLightStatusBar) {
            systemUiVisibilityFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            systemUiVisibilityFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = systemUiVisibilityFlags
    }
}