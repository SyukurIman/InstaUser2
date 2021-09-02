package com.example.instauser

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instauser.RecyclerViewItems.ListAdapter
import com.example.instauser.RecyclerViewItems.MainView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ListAdapter
    private lateinit var mainView: MainView
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainView::class.java)

        navView.setupWithNavController(navController)

        connection()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search){
            val intent = Intent(this@MainActivity, Search_Resut::class.java)
            startActivity(intent)
        }
        if(item.itemId == R.id.language){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun connection(){
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected){
            Toast.makeText(applicationContext, "Connect", Toast.LENGTH_LONG).show()
        }else{
            val alert = AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_alert))
                .setMessage(getString(R.string.message_alert))
                .setPositiveButton(R.string.close) { dialogInterface, i -> finish() }
                .setNegativeButton(getString(R.string.try_again)){dialog, which -> connection() }
            alert.setCancelable(false)
            alert.show()
        }
    }
}