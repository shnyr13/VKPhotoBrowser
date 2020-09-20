package ru.padev.vkclient.core.di

import android.content.Context
import ru.padev.vkclient.core.di.provider.CoreProvider

interface DaggerApplication {

    fun getApplicationContext(): Context

    fun getApplicationProvider(): CoreProvider
}