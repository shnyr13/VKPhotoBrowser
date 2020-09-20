package ru.padev.vkclient.main.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.padev.vkclient.main.ui.auth.AuthFragment
import ru.padev.vkclient.main.ui.friends.friend_details.FriendDetailsFragment
import ru.padev.vkclient.main.ui.friends.friends_list.FriendsListFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeAuthFragment(): AuthFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendsListFragment(): FriendsListFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendDetailsFragment(): FriendDetailsFragment
}