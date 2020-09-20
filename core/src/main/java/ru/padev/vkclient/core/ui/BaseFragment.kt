package ru.padev.vkclient.core.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.padev.vkclient.core.R
import ru.padev.vkclient.core.di.DaggerApplication
import ru.padev.vkclient.core.di.provider.CoreProvider
import ru.padev.vkclient.core.presentation.BaseViewModel
import ru.padev.vkclient.core.presentation.events.ErrorEvent
import ru.padev.vkclient.core.presentation.events.ErrorResEvent
import ru.padev.vkclient.core.presentation.events.Event
import ru.padev.vkclient.core.presentation.events.ViewEvent


abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    abstract val viewModel: BaseViewModel

    val coreComponent: CoreProvider by lazy {
        (requireActivity().applicationContext as DaggerApplication).getApplicationProvider()
    }

    private val renderDisposable = CompositeDisposable()

    abstract fun injectDependencies()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeEventLiveData()

        getActionBar()?.setDisplayHomeAsUpEnabled(parentFragmentManager.backStackEntryCount > 0)

        if (requireParentFragment().tag == getString(R.string.nav_host_fragment_tag))
            applyInsets(requireParentFragment().requireView())
    }

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun onDestroyView() {
        renderDisposable.dispose()
        super.onDestroyView()
    }

    fun Disposable.addToDisposables() {
        renderDisposable.add(this)
    }

    protected open fun applyInsets(view: View) {

        view.applySystemWindowInsetsToMargin(bottom = true, consume = false)
        view.applySystemWindowInsetsToPadding(top = true, consume = false)
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
//        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).apply {
//            view.translationY = -requireParentFragment().requireView().paddingBottom.toFloat()
//            show()
//        }
        requireActivity().supportFragmentManager.findFragmentByTag(getString(R.string.nav_host_fragment_tag))
            ?.let {
                val snack = Snackbar.make(it.requireView(), message, Snackbar.LENGTH_SHORT)
                snack.view.translationY = it.requireView().paddingBottom.toFloat()
                snack.show()
            }
    }

    protected open fun handleEvent(event: ViewEvent) {
        if (event is ErrorEvent) {
            showError(event.message)
            return
        } else if (event is ErrorResEvent) {
            showError(getString(event.messageRes))
            return
        }
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
}