package ru.padev.vkclient.main.ui

import android.os.Bundle
import ru.padev.vkclient.core.presentation.events.ViewEvent

data class NavigationEvent(
    val navScreenResId: Int,
    val bundle: Bundle?
) : ViewEvent