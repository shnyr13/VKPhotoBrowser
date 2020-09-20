package ru.padev.vkclient.core.ui.difflist.internal

interface EqualityFunction<T> {
    fun equal(a: T, b: T): Boolean
}