package ru.padev.vkclient.core.ui.tabs

import androidx.fragment.app.Fragment

interface TabsDataSource {
    fun getFragment(position: Int): Fragment

    fun getTitleResource(position: Int): String

    val counts: Int
}