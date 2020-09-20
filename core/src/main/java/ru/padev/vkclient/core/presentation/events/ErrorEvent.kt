package ru.padev.vkclient.core.presentation.events

data class ErrorEvent(
    val message: String
) : ViewEvent