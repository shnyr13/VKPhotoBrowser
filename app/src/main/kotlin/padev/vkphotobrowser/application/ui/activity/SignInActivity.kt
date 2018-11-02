package padev.vkphotobrowser.application.ui.activity

import android.os.Bundle
import padev.vkphotobrowser.R
import padev.vkphotobrowser.core.view.BaseActivity

class SignInActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)
    }
}