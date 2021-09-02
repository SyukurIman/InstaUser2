package com.example.instauser.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instauser.DetailItems.DetailActivity
import com.example.instauser.DetailItems.FollowerFragment
import com.example.instauser.DetailItems.FollowingFragment
import com.example.instauser.ImageSlider.BannerAdapter
import com.example.instauser.ImageSlider.bannerList
import com.example.instauser.R
import com.example.instauser.RecyclerViewItems.ItemsView
import com.example.instauser.RecyclerViewItems.ListAdapter
import com.example.instauser.RecyclerViewItems.MainView
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator3

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var itemsView: ItemsView
    private lateinit var adapter: ListAdapter
    private lateinit var mainView: MainView
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var pageIndicatorView: CircleIndicator3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        bannerAdapter = BannerAdapter()
        bannerAdapter.notifyDataSetChanged()
        bannerAdapter.setData(bannerList)
        banner_view_pager.adapter = bannerAdapter

        pageIndicatorView = view.findViewById(R.id.indicator)
        pageIndicatorView.setViewPager(banner_view_pager)
        pageIndicatorView.animatePageSelected(2)

        adapter = ListAdapter()
        adapter.notifyDataSetChanged()
        recylerView.layoutManager = LinearLayoutManager(context)
        recylerView.adapter = adapter
        setListClickAction()

        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainView::class.java)
        mainView.setListUserHome()
        itemsView = ItemsView()
        mainView.getList().observe(viewLifecycleOwner, Observer { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
                showLoading(false)
            }
        })

    }
    private fun setListClickAction() {
        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClick(itemsView: ItemsView) {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, itemsView.name)
                    putExtra(DetailActivity.EXTRA_AVATAR, itemsView.avatar)
                    putExtra(FollowerFragment.EXTRA_NAMME, itemsView.name)
                    putExtra(FollowingFragment.EXTRA_NAMME, itemsView.name)
                }
                startActivity(intent)
            }
        })
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE

        }
    }

}