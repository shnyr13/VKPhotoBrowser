package ru.padev.vkclient.core.di.module

import com.google.gson.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.padev.vkclient.core.BuildConfig
import ru.padev.vkclient.core.prefs.AuthPrefs
import timber.log.Timber
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.Throws

@Module
class NetworkModule {

    private val requestTimeOutTime: Long = 20

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("http://yandex.ru")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideClientBuilder(prefs: AuthPrefs): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.readTimeout(requestTimeOutTime, TimeUnit.SECONDS)
            .writeTimeout(requestTimeOutTime, TimeUnit.SECONDS)
            .connectTimeout(requestTimeOutTime, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) builder.addNetworkInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setPrettyPrinting()
            .serializeNulls()
            .create()
    }
}