package ru.padev.vkclient.core.di.provider

import ru.padev.vkclient.core.database.VkClientAppDatabase

interface DBProvider {

    fun provideDatabase(): VkClientAppDatabase
}