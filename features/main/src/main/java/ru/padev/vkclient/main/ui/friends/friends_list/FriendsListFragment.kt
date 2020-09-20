package ru.padev.vkclient.main.ui.friends.friends_list

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_friends_list.*
import ru.padev.vkclient.core.presentation.events.ViewEvent
import ru.padev.vkclient.core.ui.BaseFragment
import ru.padev.vkclient.main.R
import ru.padev.vkclient.main.di.component.MainFeatureComponent
import ru.padev.vkclient.main.ui.NavigationEvent
import ru.padev.vkclient.core.ui.difflist.internal.DiffList
import ru.padev.vkclient.main.data.vo.FriendVO
import ru.padev.vkclient.main.ui.friends.friends_list.adapter.FriendListAdapter
import ru.padev.vkclient.main.ui.friends.friends_list.adapter.FriendListItemEquality
import javax.inject.Inject

class FriendsListFragment: BaseFragment(R.layout.fragment_friends_list) {
    private lateinit var mFriendsListAdapter: FriendListAdapter

    override fun injectDependencies() {
        MainFeatureComponent.Builder.build(
            coreComponent
        ).inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: FriendsListViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(getString(R.string.friends_list_title))

        getActionBar()?.setDisplayHomeAsUpEnabled(false)

        /*requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //do nothing
            }
        })*/

        setupViews()

        subscribeLiveData()

        viewModel.refreshFriends()
    }

    override fun handleEvent(event: ViewEvent) {
        super.handleEvent(event)

        when (event) {
            is NavigationEvent -> findNavController().navigate(event.navScreenResId, event.bundle)
        }
    }

    private fun setupViews() {
        friends_swipe_refresh.setOnRefreshListener {
            viewModel.refreshFriends()
        }

        mFriendsListAdapter = FriendListAdapter (
            viewModel.friendDidClickSubject,
            FriendListItemEquality.identityEquality,
            FriendListItemEquality.contentEquality
        )

        friends_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mFriendsListAdapter
        }

    }

    private fun subscribeLiveData() {
        val friendsObserver = Observer<List<FriendVO>> {
            mFriendsListAdapter.apply(it, DiffList.Type.OVERWRITE)
        }

        val loadingObserver = Observer<Boolean> {
            friends_swipe_refresh.isRefreshing = it
        }

        viewModel.apply {
            friends.observe(viewLifecycleOwner, friendsObserver)
            loading.observe(viewLifecycleOwner, loadingObserver)
        }
    }
}