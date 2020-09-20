package ru.padev.vkclient.core.ui.difflist.adapter

import ru.padev.vkclient.core.ui.difflist.internal.EqualityFunction

interface Equality<E> {
    val identityEquality: EqualityFunction<in E>
    val contentEquality: EqualityFunction<in E>
}