package padev.vkphotobrowser.application.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import padev.vkphotobrowser.R
import padev.vkphotobrowser.application.mvp.presenter.sign_in.ISingInPresenter
import padev.vkphotobrowser.application.mvp.presenter.sign_in.SingInPresenter
import padev.vkphotobrowser.application.mvp.view.ISignInView
import padev.vkphotobrowser.core.view.BaseActivity

class SignInActivity: BaseActivity(), ISignInView {

    private val TAG: String = SignInActivity::class.java.simpleName

    private val presenter: ISingInPresenter = SingInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!presenter.loginVK(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun signInButtonClicked(view: View) {
        presenter.signIn(this, this.packageName)


    }

    override fun startLoading() {

    }

    override fun endLoading() {

    }

    override fun showError(errorText: Int) {
        Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
    }

    override fun showFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }

}