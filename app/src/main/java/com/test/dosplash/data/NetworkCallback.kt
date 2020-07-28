package com.test.dosplash.data

import android.text.TextUtils
import com.google.gson.Gson
import com.test.dosplash.error.CommonResponse
import com.test.dosplash.error.FailureResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
abstract class NetworkCallback<T> : Callback<T> {
    val AUTH_FAILED = 99

    abstract fun onSuccess(t: T)

    abstract fun onFailure(failureResponse: FailureResponse?)

    abstract fun onError(t: Throwable?)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            val failureErrorBody: FailureResponse =
                getFailureErrorBody(response)!!
            onFailure(failureErrorBody)
        }
    }


    /**
     * Create your custom failure response out of server response
     * Also save Url for any further use
     */
    private fun getFailureErrorBody(
        errorBody: Response<T>
    ): FailureResponse? {
        val failureResponse = FailureResponse()
        return if (errorBody.code() >= 500 || errorBody.code() == 404 || errorBody.errorBody() == null) {
            failureResponse.errorCode = errorBody.code()
            failureResponse.errorMessage = errorBody.message()
            failureResponse
        } else {
            var commonResponse: CommonResponse? = null
            try {
                val error = errorBody.errorBody()!!.string()
                commonResponse = Gson().fromJson<CommonResponse>(error, CommonResponse::class.java)
                if (commonResponse != null) {
                    failureResponse.responseType =
                        (if (TextUtils.isEmpty(commonResponse.responseType)) commonResponse.type else commonResponse.responseType)
                    failureResponse.errorMessage = commonResponse.message
                    failureResponse.errorCode = commonResponse.statusCode
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            failureResponse
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        if (t is SocketTimeoutException || t is UnknownHostException) {
            val failureResponseForNoNetwork = getFailureResponseForNoNetwork()
            onFailure(failureResponseForNoNetwork)
        } else {
            onError(t)
        }
    }

    private  fun getFailureResponseForNoNetwork(): FailureResponse {
        val failureResponse = FailureResponse()
        failureResponse.errorMessage = "No Network"
        failureResponse.errorCode = 28
        return failureResponse
    }
}
