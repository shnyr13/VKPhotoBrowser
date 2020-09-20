package ru.padev.vkclient.core.di.provider

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface NetworkProvider {

    fun provideHttpClient(): OkHttpClient
    fun provideRetrofit(): Retrofit
    fun provideGson(): Gson
}