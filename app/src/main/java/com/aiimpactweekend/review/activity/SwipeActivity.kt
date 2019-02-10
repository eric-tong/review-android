package com.aiimpactweekend.review.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.adapter.CardAdapter
import com.aiimpactweekend.review.constants.Direction
import com.aiimpactweekend.review.constants.sampleApplicant
import com.aiimpactweekend.review.generator.generateApplicant
import com.aiimpactweekend.review.listener.SwipeListener
import com.aiimpactweekend.review.model.Applicant
import com.aiimpactweekend.review.util.setFullscreen
import com.aiimpactweekend.review.util.shift
import com.aiimpactweekend.review.view.CardView
import kotlinx.android.synthetic.main.activity_swipe.*

class SwipeActivity : AppCompatActivity() {

    private val applicants: MutableList<Applicant> = mutableListOf(sampleApplicant)

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
            if (view is CardView) {
                view.setApplicant(applicants[0])
            }
        }

        override fun loadNext(view: View, index: Int) {
            if (view is CardView) {
                while(applicants.size < 2) applicants.add(generateApplicant())
                view.setApplicant(applicants[1])
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

        override fun onFling(direction: Direction) {
            val applicant = applicants[0]
            if (applicant.isRecommended && direction == Direction.LEFT) {
                val message = "${applicant.firstName} might be a good fit for your role"
                Snackbar.make(container, message, Snackbar.LENGTH_LONG)
                    .setAction("Review", View.OnClickListener {  })
                    .show()
            }
            applicants.removeAt(0)
        }

        override fun onUnsuccessfulFling() {

        }
    }
}
