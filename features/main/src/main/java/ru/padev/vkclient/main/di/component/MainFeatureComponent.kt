package ru.padev.vkclient.main.di.component

import dagger.Component
import ru.padev.vkclient.core.di.provider.CoreProvider
import ru.padev.vkclient.main.di.module.AssistedInjectModule
import ru.padev.vkclient.main.di.module.MainModule
import ru.padev.vkclient.main.di.provider.MainProvider
import ru.padev.vkclient.main.ui.auth.AuthFragment
import ru.padev.vkclient.main.ui.friends.friend_details.FriendDetailsFragment
import ru.padev.vkclient.main.ui.friends.friends_list.FriendsListFragment
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CoreProvider::class
    ],
    modules = [
        MainModule::class,
        AssistedInjectModule::class
    ]
)
interface MainFeatureComponent : MainProvider {

    fun inject(target: AuthFragment)
    fun inject(target: FriendsListFragment)
    fun inject(target: FriendDetailsFragment)

    class Builder private constructor() {

        companion object {

            private var component: MainFeatureComponent? = null

            fun build(coreProvider: CoreProvider): MainFeatureComponent {

                if (component == null) {
                    component = DaggerMainFeatureComponent.builder()
                        .coreProvider(coreProvider)
                        .build()
                }
                return component!!
            }
        }
    }
}