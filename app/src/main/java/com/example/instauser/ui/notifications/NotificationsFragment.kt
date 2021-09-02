package com.example.instauser.ui.notifications


import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instauser.R
import kotlinx.android.synthetic.main.activity_search__resut.progressBar
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object : CountDownTimer(200, 50 ){
            override fun onTick(millisUntilFinished: Long) {
                showLoading(true)
            }

            override fun onFinish() {
                showLoading(false)
                text_about.visibility = View.VISIBLE
            }
        }
        timer.start()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}