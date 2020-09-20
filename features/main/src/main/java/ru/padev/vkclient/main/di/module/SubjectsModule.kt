package ru.padev.vkclient.main.di.module

import dagger.Module
import dagger.Provides
import ru.padev.vkclient.main.presentation.LaunchAppMessageLoadingSubject
import javax.inject.Singleton

@Module
class SubjectsModule {

    @Provides
    @Singleton
    fun provideLaunchAppMessageLoadingSubject(): LaunchAppMessageLoadingSubject {
        return LaunchAppMessageLoadingSubject()
    }
}