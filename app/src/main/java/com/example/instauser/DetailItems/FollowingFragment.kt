package com.example.instauser.DetailItems

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instauser.R
import com.example.instauser.RecyclerViewItems.ItemsView
import com.example.instauser.RecyclerViewItems.ListAdapter
import com.example.instauser.RecyclerViewItems.MainView
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var adapter: ListAdapter
    private lateinit var mainView: MainView

    companion object {
        const val EXTRA_NAMME = "extra_namme"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter()
        adapter.notifyDataSetChanged()

        recylerViewFollowing.layoutManager = LinearLayoutManager(context)
        recylerViewFollowing.adapter = adapter
        recylerViewFollowing.setHasFixedSize(true)
        setListClickAction()

        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainView::class.java)

        val message = activity?.intent?.getStringExtra(EXTRA_NAMME)
        if (message != null) {
            mainView.setFollowingUser(message)
        } else {
            Log.d(message,"Error" )
        }

        mainView.getList().observe(viewLifecycleOwner, Observer { userItem ->
            if (userItem != null) {
                adapter.setData(userItem)
            }
        })
    }

    private fun setListClickAction() {
        adapter.setOnItemClickCallback(object :
            ListAdapter.OnItemClickCallback {
            override fun onItemClick(itemsView: ItemsView) {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, itemsView.name)
                    putExtra(DetailActivity.EXTRA_AVATAR, itemsView.avatar)
                    putExtra(FollowerFragment.EXTRA_NAMME, itemsView.name)
                }
                startActivity(intent)
            }
        })
    }

}