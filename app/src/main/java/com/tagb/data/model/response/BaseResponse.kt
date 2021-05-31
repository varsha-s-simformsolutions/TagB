package com.tagb.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success")
    @Expose val success: Boolean,
    @SerializedName("message")
    @Expose val message: String,
    @SerializedName("data")
    @Expose val data: T?
)
