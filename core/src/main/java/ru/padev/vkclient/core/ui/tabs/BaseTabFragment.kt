package ru.padev.vkclient.core.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import ru.padev.vkclient.core.ui.BaseFragment

abstract class BaseTabFragment(layoutRes: Int) : BaseFragment(layoutRes) {
    var mAdapter: ViewPagerAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    protected abstract fun getViewPager(): ViewPager

    protected abstract fun getDataSource(): TabsDataSource

    private fun setupViews() {
        if (mAdapter == null) {
            mAdapter = ViewPagerAdapter(childFragmentManager)
        }
        getViewPager().adapter = mAdapter
        getViewPager().offscreenPageLimit = getDataSource().counts
    }

    inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment
                = getDataSource().getFragment(position)

        override fun getCount(): Int
                = getDataSource().counts

        override fun getPageTitle(position: Int): CharSequence
                = getDataSource().getTitleResource(position)
    }
}