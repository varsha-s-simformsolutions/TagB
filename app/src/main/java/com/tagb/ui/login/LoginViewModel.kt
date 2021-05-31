package com.tagb.ui.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.tagb.R
import com.tagb.base.BaseViewModel
import com.tagb.data.model.response.BaseResponse
import com.tagb.data.model.response.LoginRequest
import com.tagb.data.model.response.LoginResponse
import com.tagb.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var status = MutableLiveData<Boolean?>()

    val validationLiveData = MutableLiveData<ErrorMessage>()
    val loginResponseLiveData = MutableLiveData<Resource<BaseResponse<LoginResponse>>>()

    val res: LiveData<Resource<BaseResponse<LoginResponse>>>
        get() = loginResponseLiveData


    @SuppressLint("SupportAnnotationUsage")
    data class ErrorMessage(
        @StringRes val emptyEmail: Int = 0,
        @StringRes val invalidEmail: Int = 0,
        @StringRes val emptyPassword: Int = 0,
        @StringRes val isValid: Int = 0
    )

    fun isUserInputValid(): Boolean {
        return when {
            email.value.isNullOrEmpty() -> {
                validationLiveData.postValue(ErrorMessage(emptyEmail = R.string.message_null_email))
                false
            }
            (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()) -> {
                validationLiveData.postValue(ErrorMessage(invalidEmail = R.string.message_invalid_email))
                false
            }
            password.value.isNullOrEmpty() -> {
                validationLiveData.postValue(ErrorMessage(emptyPassword = R.string.message_null_password))
                false
            }
            else -> {
                validationLiveData.postValue(ErrorMessage(isValid = R.string.isValid))
                true
            }
        }
    }

    fun callLoginAPI() {
        loginResponseLiveData.value = Resource.loading()
        email.value?.let { emailid ->
            password.value?.let { pass ->
                LoginRequest(emailid, pass)
                call(mainRepository.loginHelp(LoginRequest(emailid, pass)), object : ApiCallbackListener<BaseResponse<LoginResponse>> {
                    override fun onSuccess(data: BaseResponse<LoginResponse>?) {
                        loginResponseLiveData.value = Resource.success(data)
                        status.value = true
                    }
                    override fun onError(throwable: Throwable) {
                        loginResponseLiveData.value = Resource.error(throwable)
                        status.value = false
                    }
                })
            }
        }
    }

}

