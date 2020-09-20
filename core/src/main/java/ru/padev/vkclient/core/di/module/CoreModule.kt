package ru.padev.vkclient.core.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.padev.vkclient.core.di.DaggerApplication
import javax.inject.Singleton

@Module
class CoreModule {


    @Singleton
    @Provides
    fun provideApplicationContext(application: DaggerApplication): Context {
        return application.getApplicationContext()
    }

    @Singleton
    @Provides
    fun providePreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("def_prefs", Context.MODE_PRIVATE)
    }

}