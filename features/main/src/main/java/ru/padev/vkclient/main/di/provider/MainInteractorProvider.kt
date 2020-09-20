package ru.padev.vkclient.main.di.provider

import ru.padev.vkclient.main.interactor.FriendsInteractor
import ru.padev.vkclient.main.interactor.FriendsSyncInteractor
import ru.padev.vkclient.main.network.IRestApi

interface MainInteractorProvider {

    fun provideFriendsInteractor(): FriendsInteractor
    fun provideIResetApi(): IRestApi
}