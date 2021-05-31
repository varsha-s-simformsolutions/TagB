package com.tagb.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("success")
    @Expose val success: Boolean = false,
    @SerializedName("message")
    @Expose val message: String,
    @SerializedName("data")
    @Expose val data: String,
)

