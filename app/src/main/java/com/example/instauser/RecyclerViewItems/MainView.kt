package com.example.instauser.RecyclerViewItems


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainView : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<ItemsView>>()

    fun setListUserSearh(UserName: String) {
        val listItems = ArrayList<ItemsView>()
        val url = "https://api.github.com/search/users?q=$UserName"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "309a05ea30d65d60dfee10794405738cbe7e8c98")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val jsonArray = responseObject.getJSONArray("items")

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userItem = ItemsView()
                        userItem.avatar = jsonObject.getString("avatar_url")
                        userItem.url = jsonObject.getString("url")
                        userItem.name = jsonObject.getString("login")
                        userItem.type = jsonObject.getString("type")
                        listItems.add(userItem)
                    }

                    listUser.postValue(listItems)
                }catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("OnFailure", error?.message.toString())
            }
        })
    }

    fun setListUserHome() {
        val listItems = ArrayList<ItemsView>()
        val url = "https://api.github.com/users/sidiqpermana/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "309a05ea30d65d60dfee10794405738cbe7e8c98")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until 10) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userItem = ItemsView()
                        userItem.avatar = jsonObject.getString("avatar_url")
                        userItem.url = jsonObject.getString("url")
                        userItem.name = jsonObject.getString("login")
                        userItem.type = jsonObject.getString("type")
                        listItems.add(userItem)
                    }

                    listUser.postValue(listItems)
                }catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("OnFailure", error?.message.toString())
            }
        })
    }

    fun setFollowerUser(UserName: String?) {
        val listItems = ArrayList<ItemsView>()
        val url = "https://api.github.com/users/$UserName/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "309a05ea30d65d60dfee10794405738cbe7e8c98")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userItem =
                            ItemsView()
                        userItem.avatar = jsonObject.getString("avatar_url")
                        userItem.url = jsonObject.getString("url")
                        userItem.name = jsonObject.getString("login")
                        userItem.type = jsonObject.getString("type")
                        listItems.add(userItem)
                    }
                    listUser.postValue(listItems)
                }catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("OnFailure", error?.message.toString())

            }
        })
    }

    fun setFollowingUser(UserName: String?) {
        val listItems = ArrayList<ItemsView>()
        val url = "https://api.github.com/users/$UserName/following"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "309a05ea30d65d60dfee10794405738cbe7e8c98")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userItem =
                            ItemsView()
                        userItem.avatar = jsonObject.getString("avatar_url")
                        userItem.url = jsonObject.getString("url")
                        userItem.name = jsonObject.getString("login")
                        userItem.type = jsonObject.getString("type")
                        listItems.add(userItem)
                    }
                    listUser.postValue(listItems)
                }catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("OnFailure", error?.message.toString())
            }
        })
    }

    fun getList(): LiveData<ArrayList<ItemsView>> {
        return listUser
    }


}