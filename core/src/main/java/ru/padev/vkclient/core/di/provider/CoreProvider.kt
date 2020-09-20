package ru.padev.vkclient.core.di.provider

import ru.padev.vkclient.core.prefs.AuthPrefs

interface CoreProvider : ContextProvider, NetworkProvider, DBProvider {

    fun provideAuthPrefs(): AuthPrefs
}