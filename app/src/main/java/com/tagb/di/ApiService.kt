package com.tagb.di

import com.tagb.data.model.response.BaseResponse
import com.tagb.data.model.response.LoginRequest
import com.tagb.data.model.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/login")
    fun loginAsync(@Body loginRequest: LoginRequest) : Deferred<Response<BaseResponse<LoginResponse>>>
}