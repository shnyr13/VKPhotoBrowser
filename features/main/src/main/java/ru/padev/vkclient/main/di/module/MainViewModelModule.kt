package ru.padev.vkclient.main.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.padev.vkclient.core.di.ViewModelKey
import ru.padev.vkclient.main.ui.auth.AuthViewModel
import ru.padev.vkclient.main.ui.friends.friends_list.FriendsListViewModel

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendsListViewModel::class)
    abstract fun bindFriendsListViewModel(friendsListViewModel: FriendsListViewModel): ViewModel
}