package com.example.sdustore.ui.loginRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdustore.data.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _login = MutableStateFlow<Resource<FirebaseUser>>(Resource.UnSpecified())
    val login = _login.asStateFlow()

    private val _resetPassword = MutableSharedFlow<Resource<String>>()
    val resetPassword = _resetPassword.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                if (email.isEmpty() || password.isEmpty()) {
                    _login.emit(Resource.Error("Email and Password can not be empty"))
                    return@launch
                }
                _login.emit(Resource.Loading())
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user?.let {
                    _login.emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                _login.emit(Resource.Error(e.message.toString()))
            }
        }
    }


    fun resetPassword(email: String) {
        viewModelScope.launch {
            try {
                _resetPassword.emit(Resource.Loading())
                firebaseAuth.sendPasswordResetEmail(email).await()
                _resetPassword.emit(Resource.Success(email))
            } catch (e: Exception) {
                _resetPassword.emit(Resource.Error(e.message.toString()))
            }
        }
    }

}