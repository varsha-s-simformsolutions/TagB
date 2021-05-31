package com.tagb.repository

import com.tagb.data.model.response.LoginRequest
import com.tagb.di.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiService) {
    fun loginHelp(loginReq : LoginRequest) = apiHelper.loginAsync(loginReq)
}