package ru.padev.vkclient.core.ui.difflist

object Preconditions {
    fun <T> checkNotNull(o: T, varName: String): T {
        if (o == null) {
            throw NullPointerException(varName)
        }
        return o
    }
}