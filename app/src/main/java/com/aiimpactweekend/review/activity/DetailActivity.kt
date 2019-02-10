package com.aiimpactweekend.review.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aiimpactweekend.review.view.CardView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(CardView(this).apply { setApplicant(intent.getParcelableExtra("Applicant")) })
    }
}
