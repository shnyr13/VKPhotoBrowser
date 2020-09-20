package ru.padev.vkclient.main.ui.friends.friend_details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_friend_details.*
import ru.padev.vkclient.core.presentation.assistedViewModels
import ru.padev.vkclient.core.presentation.events.ViewEvent
import ru.padev.vkclient.core.ui.BaseFragment
import ru.padev.vkclient.main.R
import ru.padev.vkclient.main.data.vo.FriendDetailsVO
import ru.padev.vkclient.main.data.vo.FriendVO
import ru.padev.vkclient.main.di.component.MainFeatureComponent
import ru.padev.vkclient.main.ui.NavigationEvent
import ru.padev.vkclient.main.utils.MainFeatureConstants
import javax.inject.Inject

class FriendDetailsFragment : BaseFragment (R.layout.fragment_friend_details){
    override fun injectDependencies() {
        MainFeatureComponent.Builder.build(
            coreComponent
        ).inject(this)
    }

    @Inject
    lateinit var viewModelFactory: FriendDetailsViewModel.Factory

    override val viewModel: FriendDetailsViewModel by assistedViewModels {
        viewModelFactory.get(getArgsExtra())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(getString(R.string.friend_title))

        setupViews()

        subscribeLiveData()

        viewModel.start()
    }

    override fun handleEvent(event: ViewEvent) {
        super.handleEvent(event)

        when (event) {
            is NavigationEvent -> requireParentFragment().requireParentFragment().let {

            }
        }
    }

    private fun setupViews() {
        friends_details_swipe_refresh.setOnRefreshListener {
            viewModel.start()
        }
    }

    private fun subscribeLiveData() {
        val friendDetailsObserver = Observer<FriendDetailsVO> {
            setToolbar(it.fullName)

            Picasso.get()
                .load(it.photoMax)
                .placeholder(R.drawable.ic_placeholder)
                .fit()
                .centerInside()
                .into(image_friend_details)

            image_online.visibility = if (it.online) View.VISIBLE else View.GONE

            friend_country.text = it.country
            friend_city.text = it.city
        }

        val loadingObserver = Observer<Boolean> {
            friends_details_swipe_refresh.isRefreshing = it
        }

        viewModel.apply {
            friendDetails.observe(viewLifecycleOwner, friendDetailsObserver)
            loading.observe(viewLifecycleOwner, loadingObserver)
        }
    }

    private fun getArgsExtra(): FriendVO {
        return arguments?.get(MainFeatureConstants.ExtraData.Friend.DATA) as? FriendVO ?: FriendVO.defaultValue
    }
}