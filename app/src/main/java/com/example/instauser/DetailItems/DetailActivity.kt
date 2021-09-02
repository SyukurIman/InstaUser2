package com.example.instauser.DetailItems

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.instauser.R
import com.example.instauser.RecyclerViewItems.MainView
import com.google.android.material.tabs.TabLayout
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private lateinit var mainView: MainView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainView::class.java)

        supportActionBar?.title = intent.getStringExtra(EXTRA_NAME)
        setContentDetail()

    }

    private fun setContentDetail() {
        val userName = intent?.getStringExtra(EXTRA_NAME)
        progressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_AVATAR))
            .into(tv_image)
        try {
            val detailPagerAdapter =
                DetailPagerAdapter(
                    this,
                    supportFragmentManager
                )
            val viewPager: ViewPager = findViewById(R.id.view_pager)
            viewPager.adapter = detailPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            tabs.setupWithViewPager(viewPager)
        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "309a05ea30d65d60dfee10794405738cbe7e8c98")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${userName}"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)

                    val fullName = responseObject.getString("name")
                    val locationUser = responseObject.getString("location")
                    val websiteUser = responseObject.getString("blog")

                    tv_fullName.text = fullName
                    location.text = locationUser
                    Website.text = websiteUser
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                progressBar.visibility = View.INVISIBLE
                Log.d("OnFailure", error?.message.toString())
            }
        })
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}