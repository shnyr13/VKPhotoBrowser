package ru.padev.vkclient.main.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.fragment_auth.*
import ru.padev.vkclient.core.ui.BaseFragment
import ru.padev.vkclient.main.R
import ru.padev.vkclient.main.di.component.MainFeatureComponent
import javax.inject.Inject

class AuthFragment : BaseFragment(R.layout.fragment_auth) {
    override fun injectDependencies() {
        MainFeatureComponent.Builder.build(
            coreComponent
        ).inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth_button.setOnClickListener {
            viewModel.auth(requireActivity())
        }
    }

    override fun onResume() {
        super.onResume()

        if (VKSdk.isLoggedIn()) {
            findNavController().navigate(R.id.navigate_to_friends_list)
        }
    }
}