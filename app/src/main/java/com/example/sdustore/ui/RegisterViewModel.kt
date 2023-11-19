package com.example.sdustore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.Resource
import com.example.sdustore.data.User
import com.example.sdustore.data.extensions.RegisterFieldsState
import com.example.sdustore.data.extensions.RegisterValidation
import com.example.sdustore.data.extensions.validateEmail
import com.example.sdustore.data.extensions.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _register = MutableStateFlow<Resource<FirebaseUser>>(Resource.Loading())
    val register = _register.asStateFlow()

    private val _validation = Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailPassword(user: User, password: String) {
        if (checkValidation(user, password)) {
            firebaseAuth.createUserWithEmailAndPassword(user.email, password) //create Account
                .addOnSuccessListener {
                    it.user?.let {
                        _register.value = Resource.Success(it)
                    }
                }.addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())
                }
        } else {
            val registerFieldsState = RegisterFieldsState(
                validateEmail(user.email),
                validatePassword(password)
            )
            viewModelScope.launch {
                _validation.send(registerFieldsState)
            }
        }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        return emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
    }
}