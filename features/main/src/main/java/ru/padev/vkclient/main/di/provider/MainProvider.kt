package ru.padev.vkclient.main.di.provider

import ru.padev.vkclient.main.presentation.LaunchAppMessageLoadingSubject

interface MainProvider : MainInteractorProvider {

    fun provideLaunchAppMessageLoadingSubject(): LaunchAppMessageLoadingSubject
}