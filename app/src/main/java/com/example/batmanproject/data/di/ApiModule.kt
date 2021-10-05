package com.example.batmanproject.data.di

import com.example.batmanproject.BuildConfig
import com.example.batmanproject.data.webservice.ChuckInterceptor
import com.example.batmanproject.data.webservice.LoggingInterceptor
import com.example.batmanproject.data.webservice.MovieApi
import com.example.batmanproject.data.webservice.RetrofitServiceBuilder
import com.example.batmanproject.util.Constants
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val apiModule = module {

    single(named("default")) {
        RetrofitServiceBuilder.buildRetrofit(
            okHttpClient = get(named("default")),
            baseUrl = Constants.BASE_URL
        )
    }

    single(named("default")) {
        val okHttpBuilder = OkHttpClient().newBuilder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor.getInstance(androidApplication()))
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(LoggingInterceptor.getInstance())
        }
        okHttpBuilder.build()
    }

    single {
        RetrofitServiceBuilder.buildService(
            retrofit = get(named("default")),
            service = MovieApi::class.java
        )
    }
}