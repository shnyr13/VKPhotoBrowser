package ru.padev.vkclient.main.ui.auth

import android.app.Activity
import ru.padev.vkclient.core.presentation.BaseViewModel
import ru.padev.vkclient.main.interactor.AuthorizationInteractor
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    val mAuthorizationInteractor: AuthorizationInteractor
) : BaseViewModel() {

    fun auth(activity: Activity) {
       mAuthorizationInteractor.auth(activity)
    }
}
