package com.example.sdustore.data.extensions

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.example.sdustore.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

@SuppressLint("InflateParams")
fun Fragment.setUpBottomSheetDialog(
    onSendClick: (String) -> Unit
){
    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail = view.findViewById<TextInputEditText>(R.id.editText)
    val buttonCancel = view.findViewById<MaterialButton>(R.id.cancel_button)
    val buttonSend = view.findViewById<MaterialButton>(R.id.send_button)

    buttonSend.setSafeOnClickListener {
        val email = edEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    buttonCancel.setSafeOnClickListener {
        dialog.dismiss()
    }
}