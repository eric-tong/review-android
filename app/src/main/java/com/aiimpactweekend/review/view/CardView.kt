package com.aiimpactweekend.review.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.constants.sampleApplicant
import com.aiimpactweekend.review.drawable.CardBackground
import com.aiimpactweekend.review.model.Applicant
import kotlinx.android.synthetic.main.view_card.view.*


class CardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private var applicant: Applicant = sampleApplicant

    init {
        inflate(getContext(), R.layout.view_card, this)
        background = cardBackground
    }

    fun setApplicant(applicant: Applicant) {
        this.applicant = applicant
        nameTv.text = "${applicant.firstName} ${applicant.lastName}"
        workPositionTv.text = "${applicant.workPosition} @ ${applicant.workName}"
        workDurationTv.text = "Feb ${2019 - applicant.workDuration} - Current (${applicant.workDuration} years)"
        schoolDegreeTv.text = applicant.schoolDegree
        schoolNameTv.text = "${applicant.schoolName}, ${applicant.schoolYear}"
        arrayOf(tagViewTop, tagViewMiddle, tagViewBottom).forEachIndexed { index, tagView ->
            tagView.setTrait(applicant.traits[index])
        }
    }

    private val cardBackground: Drawable
        get() {
            val shadowRadius = context.resources.getDimension(R.dimen.card_shadow_radius)
            val padding = context.resources.getDimension(R.dimen.card_padding)
            val paddingTop = shadowRadius - (padding - shadowRadius)

            setPadding(
                padding.toInt(),
                paddingTop.toInt(),
                padding.toInt(),
                padding.toInt()
            )

            return CardBackground(context.applicationContext).toDrawable()
        }
}