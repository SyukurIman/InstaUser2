package com.example.instauser.DetailItems

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class DetailPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        FollowerFragment(),
        FollowingFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Follower"
            else -> "Following"
        }
    }

    override fun getCount(): Int {
        return pages.size
    }

}