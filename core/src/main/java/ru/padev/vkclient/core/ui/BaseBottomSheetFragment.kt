package ru.padev.vkclient.core.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.padev.vkclient.core.R
import ru.padev.vkclient.core.di.DaggerApplication
import ru.padev.vkclient.core.di.provider.CoreProvider
import ru.padev.vkclient.core.presentation.BaseViewModel
import ru.padev.vkclient.core.presentation.events.ErrorEvent
import ru.padev.vkclient.core.presentation.events.Event
import ru.padev.vkclient.core.presentation.events.ViewEvent


abstract class BaseBottomSheetFragment(val layoutRes: Int): BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            layoutRes, container,
            false
        )
    }
    abstract val viewModel: BaseViewModel

    val coreComponent: CoreProvider by lazy {
        (requireActivity().applicationContext as DaggerApplication).getApplicationProvider()
    }

    abstract fun injectDependencies()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeEventLiveData()

        getActionBar()?.setDisplayHomeAsUpEnabled(parentFragmentManager.backStackEntryCount > 0)
    }

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun onDestroy() {
        renderDisposable.dispose()
        super.onDestroy()
    }

    fun Disposable.addToDisposables() {
        renderDisposable.add(this)
    }

    protected open fun setToolbar(title: String) {
        getActionBar()?.show()
        getActionBar()?.title = title
    }

    protected open fun hideToolbar() {
        getActionBar()?.hide()
    }

    protected open fun getActionBar(): ActionBar? = (activity as AppCompatActivity).supportActionBar

    protected open fun showError(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).apply {
            view.translationY = -requireParentFragment().requireView().paddingBottom.toFloat()
            show()
        }
    }

    protected open fun handleEvent(event: ViewEvent) {
        if (event is ErrorEvent) showError(event.message)

    }

    protected open fun backClicked() {
        findNavController().navigateUp()
    }

    private fun subscribeEventLiveData() {

        val eventObserver = Observer<Event<ViewEvent>> {
            it.getContentIfNotHandled()?.let { event -> handleEvent(event) }
        }

        viewModel.apply {
            eventLiveData.observe(viewLifecycleOwner, eventObserver)
        }
    }

    private val renderDisposable = CompositeDisposable()
}