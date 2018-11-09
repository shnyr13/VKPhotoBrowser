package padev.vkphotobrowser.application.mvp.model.interactor

import android.app.Activity
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil
import padev.vkphotobrowser.application.mvp.presenter.sign_in.ISingInPresenter

class SignInInteractor (val presenter: ISingInPresenter) {

    fun signIn(activity: Activity, packageName: String) {

        VKUtil.getCertificateFingerprint(activity, packageName)

        VKSdk.login(activity, VKScope.FRIENDS)
    }
}