package ru.padev.vkclient.core.di.component

import ru.padev.vkclient.core.di.provider.CoreProvider
import dagger.BindsInstance
import dagger.Component
import ru.padev.vkclient.core.di.DaggerApplication
import ru.padev.vkclient.core.di.module.CoreModule
import ru.padev.vkclient.core.di.module.DBModule
import ru.padev.vkclient.core.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class,
        NetworkModule::class,
        DBModule::class
    ]
)
interface CoreComponent : CoreProvider {

    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun application(daggerApplication: DaggerApplication): ComponentBuilder

        fun build(): CoreComponent
    }

    class Builder private constructor() {

        companion object {

            fun build(daggerApplication: DaggerApplication): CoreProvider {

                return DaggerCoreComponent.builder()
                    .application(daggerApplication)
                    .build()
            }
        }
    }
}