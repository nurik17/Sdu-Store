package com.example.sdustore.data.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView

fun String.toHtmlSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

// Usage
/*val htmlTaggedString = "<u>Подчеркнутый текст</u>"
tvTitle.text = htmlTaggedString.toHtmlSpanned()*/
