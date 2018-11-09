package padev.vkphotobrowser.application.mvp.presenter.sign_in

import android.app.Activity
import android.content.Intent

interface ISingInPresenter {
    fun signIn(activity: Activity, packageName: String)

    fun loginVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean
}