package ru.padev.vkclient.di

import dagger.Component
import ru.padev.vkclient.main.di.component.MainFeatureComponent
import ru.padev.vkclient.main.di.provider.MainProvider
import ru.padev.vkclient.MainActivity
import ru.padev.vkclient.VkClientApp
import ru.padev.vkclient.core.di.component.CoreComponent
import ru.padev.vkclient.core.di.provider.CoreProvider
import ru.padev.vkclient.services.SyncFriendsWorker
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CoreProvider::class,
        MainProvider::class
    ]
)
interface AppComponent : CoreProvider {

    fun inject(activity: MainActivity)
    fun inject(worker: SyncFriendsWorker)

    class Builder private constructor() {

        companion object {

            fun build(application: VkClientApp): AppComponent {

                val coreProvider = CoreComponent.Builder.build(application)

                val mainProvider = MainFeatureComponent.Builder.build(coreProvider)

                return DaggerAppComponent.builder()
                    .coreProvider(coreProvider)
                    .mainProvider(mainProvider)
                    .build()
            }
        }
    }
}