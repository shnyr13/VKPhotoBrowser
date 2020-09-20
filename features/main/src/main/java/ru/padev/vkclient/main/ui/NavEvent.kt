package ru.padev.vkclient.main.ui

import androidx.annotation.NavigationRes
import ru.padev.vkclient.core.presentation.events.ViewEvent

data class NavEvent(@NavigationRes val destination: Int): ViewEvent