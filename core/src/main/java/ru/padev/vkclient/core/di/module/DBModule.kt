package ru.padev.vkclient.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.padev.vkclient.core.database.DbConstants
import ru.padev.vkclient.core.database.VkClientAppDatabase
import javax.inject.Singleton

@Module
class DBModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): VkClientAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, VkClientAppDatabase::class.java,
            DbConstants.DATABASE_NAME
        ).build()
    }
}