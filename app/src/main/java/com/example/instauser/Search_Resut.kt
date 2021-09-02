package com.example.instauser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instauser.DetailItems.DetailActivity
import com.example.instauser.DetailItems.FollowerFragment
import com.example.instauser.DetailItems.FollowingFragment
import com.example.instauser.RecyclerViewItems.ItemsView
import com.example.instauser.RecyclerViewItems.ListAdapter
import com.example.instauser.RecyclerViewItems.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search__resut.*
import kotlinx.android.synthetic.main.activity_search__resut.progressBar
import kotlinx.android.synthetic.main.fragment_home.*

class Search_Resut : AppCompatActivity() {
    private lateinit var adapter: ListAdapter
    private lateinit var mainView: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search__resut)
        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainView::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search2).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.title_searh)
        searchView.onActionViewExpanded()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter = ListAdapter()
                adapter.notifyDataSetChanged()
                showLoading(true)
                if (newText != null) {
                    showLoading(true)
                    mainView.setListUserSearh(newText)
                    recylerViewSearch.layoutManager = LinearLayoutManager(this@Search_Resut)
                    recylerViewSearch.adapter = adapter
                    RecylerSearch(true)
                    mainView.getList().observe(this@Search_Resut, Observer { userItem ->
                        if (userItem != null) {
                            adapter.setData(userItem)
                            showLoading(false)
                        }
                    })

                }
                if (newText == null|| newText.isEmpty()){
                    RecylerSearch(false)
                }
                setListClickAction()
                return false
            }
        })
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun RecylerSearch(state: Boolean): Boolean {
        if (state) {
            recylerViewSearch.visibility = View.VISIBLE
        } else {
            recylerViewSearch.visibility = View.INVISIBLE
        }
        return false
    }

    private fun setListClickAction() {
        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClick(itemsView: ItemsView) {
                val intent = Intent(this@Search_Resut, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, itemsView.name)
                    putExtra(DetailActivity.EXTRA_AVATAR, itemsView.avatar)
                    putExtra(FollowerFragment.EXTRA_NAMME, itemsView.name)
                    putExtra(FollowingFragment.EXTRA_NAMME, itemsView.name)
                }
                startActivity(intent)
            }
        })
    }
}