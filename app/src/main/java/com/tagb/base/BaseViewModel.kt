package com.tagb.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.tagb.data.model.response.ErrorResponse
import com.tagb.utils.constant.Companion.CONNECT_TO_NETWORK
import com.tagb.utils.constant.Companion.ERROR_CODE_RANGE_END
import com.tagb.utils.constant.Companion.ERROR_CODE_RANGE_START
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    var unAuthorizedEventLiveData: MutableLiveData<Boolean>? = null

    fun <T : Any> call(deferred: Deferred<Response<T>>, apiCallbackListener: ApiCallbackListener<T>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val response = deferred.await()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        apiCallbackListener.onSuccess(response.body())
                    } else {
                        if (response.code() in  ERROR_CODE_RANGE_START..ERROR_CODE_RANGE_END) {
                            if (response.code() == ERROR_CODE_RANGE_START) {
                                if (unAuthorizedEventLiveData?.value == null || unAuthorizedEventLiveData?.value == false) {
                                    apiCallbackListener.onError(Throwable(response.message()))
                                    unAuthorizedEventLiveData?.postValue(true)
                                } else {
                                    Log.i("Error", "unauthorized")
                                }
                            } else {
                                val errorResponse = Gson().fromJson<ErrorResponse>(response.errorBody()?.string(), ErrorResponse::class.java)
                                apiCallbackListener.onError(Throwable(errorResponse.message))
                            }
                        } else {
                            apiCallbackListener.onError(Throwable(response.message()))
                        }
                    }
                }
            }.onFailure {
                when (it) {
                    is HttpException -> {
                        withContext(Dispatchers.Main) {
                            apiCallbackListener.onError(it)
                        }
                    }
                    is JsonParseException -> {
                        withContext(Dispatchers.Main) {
                            apiCallbackListener.onError(it)
                        }
                    }
                    is UnknownHostException -> {
                        withContext(Dispatchers.Main) {
                            val throwable = Throwable(CONNECT_TO_NETWORK, it.cause)
                            apiCallbackListener.onError(throwable)
                        }
                    }

                }
            }
        }
    }

    /**
     *  Cancel Job
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * This interface is used to pass api callbacks
     */
    interface ApiCallbackListener<T> {
        /**
         * This fun is used to success response of apis
         */
        fun onSuccess(data: T?)

        /**
         * This fun is used to error response of apis
         */
        fun onError(throwable: Throwable)
    }

    /**
     * This class is used to pass api status
     */
    class Resource<out T>(
        val status: Status,
        val responseData: T? = null,
        val throwable: Throwable? = null
    ) {
        companion object {
            /**
             * This fun is used to return success data
             */
            fun <T> success(data: T?): Resource<T> {
                return Resource(Status.SUCCESS, data)
            }

            /**
             * This fun is used to return error data
             */
            fun <T> error(throwable: Throwable?): Resource<T> {
                return Resource(Status.ERROR, throwable = throwable)
            }

            /**
             * This fun is used to return loading
             */
            fun <T> loading(): Resource<T> {
                return Resource(Status.LOADING)
            }
        }

        override fun toString(): String {
            return "Resource(status=$status, data=$responseData, throwable=$throwable)"
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}