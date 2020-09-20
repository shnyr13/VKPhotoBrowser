package ru.padev.vkclient.main.interactor

import android.app.Activity
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInteractor @Inject constructor() {
    fun auth(activity: Activity) {
        VKUtil.getCertificateFingerprint(activity, activity.packageName)
        VKSdk.login(activity, VKScope.FRIENDS)
    }
}