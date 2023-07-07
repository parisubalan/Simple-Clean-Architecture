package com.dev.cleanarchitecture.data.remote

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dev.cleanarchitecture.common.Constants
import com.dev.cleanarchitecture.common.GetAccessTokenPojo
import com.dev.cleanarchitecture.preference.MySharedPreference
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ResponseInterceptor @Inject constructor(
    private val context: Context,
    private val mySession: MySharedPreference
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request =
                chain.request().newBuilder().header("Authorization", mySession.accessToken!!)
                    .build()
            val response = chain.proceed(request)
            val responseBody = response.peekBody(Long.MAX_VALUE)
            val responseCode = response.code
            val contentType = responseBody.contentType()
            val json: String = responseBody.string()
            val jsonObject = JSONObject(json)
            jsonObject.put("status_code", responseCode.toString())
            val body = jsonObject.toString().toResponseBody(contentType)
            when (responseCode) {
                // Success response
                200 -> return response
                // Access token expired time
                401 -> {
                    // Post get new access token request then call previous request
                    val newRequest =
                        chain.request().newBuilder().url(Constants.BASE_URL + "auth/getAccessToken")
                            .header("Authorization", mySession.refreshToken!!)
                            .build()
                    val newResponse = chain.proceed(newRequest)
                    val newResponseBody = newResponse.peekBody(Long.MAX_VALUE)
                    val newResponsePojo =
                        Gson().fromJson(newResponseBody.string(), GetAccessTokenPojo::class.java)
                    return if (newResponsePojo.status == "SUCCESS") {
                        mySession.accessToken = newResponsePojo.tokenData?.accessToken
                        chain.proceed(request)
                    } else {
                        // Here refresh token also expired to move login screen using broadcast receiver
                        mySession.clearMySession()
                        val intent = Intent("session_expired")
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
                        newResponse
                    }
                }
                // Request error response
                else -> return response.newBuilder()
                    .header("Authorization", mySession.accessToken!!)
                    .body(body)
                    .build()
            }
        } catch (e: Exception) {
            // Internet connection issue handling
            throw NoConnectivityException()
        }
    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "No network available, please check your WiFi or Data connection"
    }
}