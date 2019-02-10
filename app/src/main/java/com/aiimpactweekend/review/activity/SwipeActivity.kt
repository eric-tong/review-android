package com.aiimpactweekend.review.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.adapter.CardAdapter
import com.aiimpactweekend.review.listener.SwipeListener
import com.aiimpactweekend.review.util.setFullscreen
import kotlinx.android.synthetic.main.activity_swipe.*

class SwipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        setFullscreen()
    }

    private fun setupViews() {
        setupContainer()
    }

    private fun setupContainer() {
        container.adapter = cardAdapter
        container.swipeListener = swipeListener
        container.ready()
    }

    private val cardAdapter = object : CardAdapter {
        override fun loadCurrent(view: View, index: Int) {
            if (view is TextView) {
                view.text = "1234"
            }
        }

        override fun loadNext(view: View, index: Int) {
            if (view is TextView) {
                view.text = "1234"
            }
        }
    }

    private val swipeListener = object : SwipeListener {
        override fun isMonoFlingEnabled(): Boolean {
            return true
        }

        override fun isMultiFlingEnabled(): Boolean {
            return true
        }

        override fun isFlingSuccessful(indices: IntArray): Boolean {
            return true
        }

        override fun onFling(indices: IntArray) {

        }

        override fun onUnsuccessfulFling() {

        }
    }
}
