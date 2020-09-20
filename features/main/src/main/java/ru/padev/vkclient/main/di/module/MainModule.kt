package ru.padev.vkclient.main.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.padev.vkclient.main.network.IRestApi
import javax.inject.Singleton

@Module(
    includes = [
        MainViewModelModule::class,
        FragmentBuildersModule::class,
        ViewModelModule::class,
        SubjectsModule::class
    ]
)
class MainModule {

    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): IRestApi {
        return retrofit.create(IRestApi::class.java)
    }
}