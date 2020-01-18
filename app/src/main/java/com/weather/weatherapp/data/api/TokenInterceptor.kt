package com.weather.weatherapp.data.api

import com.weather.weatherapp.utils.Constants.API_KEY_QUERY_PARAM
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor(private val apiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, apiKey)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}