package ru.padev.vkclient.main.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.padev.vkclient.core.presentation.VkClientViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: VkClientViewModelFactory): ViewModelProvider.Factory
}