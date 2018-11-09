package padev.vkphotobrowser.application.mvp.presenter.sign_in

import android.app.Activity
import android.content.Intent
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import padev.vkphotobrowser.R
import padev.vkphotobrowser.application.mvp.model.interactor.SignInInteractor
import padev.vkphotobrowser.application.mvp.view.ISignInView

class SingInPresenter(val viewState: ISignInView): ISingInPresenter {

    val interactor = SignInInteractor(this)

    override fun signIn(activity: Activity, packageName: String) {
        interactor.signIn(activity, packageName)
    }

    override fun loginVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken?) {
                    viewState.showFriends()
                }

                override fun onError(error: VKError?) {
                    viewState.showError(R.string.login_error)
                }
            })) {
            return false
        }
        return true
    }

}