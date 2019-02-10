package com.aiimpactweekend.review.animator

import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewPropertyAnimator
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.constants.Direction
import com.aiimpactweekend.review.constants.Direction.LEFT
import com.aiimpactweekend.review.constants.Direction.RIGHT
import com.aiimpactweekend.review.constants.DragState
import com.aiimpactweekend.review.constants.isPickedUpFromTop
import com.aiimpactweekend.review.util.getPerpendicularDistanceToCorner
import com.aiimpactweekend.review.util.minAbs
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.hypot
import kotlin.math.max


class FlingAnimator(parent: View) {
    private val finalRotation =
            parent.context.resources.getInteger(R.integer.card_rotation).toDouble()
    private val thresholdTranslation =
            parent.context.resources.getDimension(R.dimen.card_fling_threshold)
    private val parentWidth by lazy { parent.width }
    private val minVelocity =
            parent.context.resources.getDimension(R.dimen.card_fling_velocity)
    private val maxVelocity =
            parent.context.resources.getDimension(R.dimen.card_max_fling_velocity)

    private var velocityTracker: VelocityTracker? = null

    fun trackMotion(event: MotionEvent) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(event)
    }

    fun resetTracker() {
        velocityTracker?.recycle()
        velocityTracker = null
    }

    fun shouldFling(view: View): Boolean {
        velocityTracker?.computeCurrentVelocity(1000, maxVelocity)
        return view.translationX.absoluteValue > thresholdTranslation ||
                absoluteVelocity > minVelocity
    }

    fun fling(view: View, dragState: DragState): ViewPropertyAnimator {
        val direction = getDirection(view)
        val translationXBy = getTranslateXBy(view, direction)
        val rotation = getRotation(dragState, direction)
        val flingDuration = getFlingDuration(translationXBy)
        val translationYBy = getTranslationYBy(flingDuration)

        Timber.d("ID %d\tFLING x: %f\ty: %f\tDuration: %d",
                view.id, translationXBy, translationYBy, flingDuration)

        return view.animate()
                .translationXBy(translationXBy)
                .translationYBy(translationYBy)
                .rotation(rotation)
                .setDuration(flingDuration)
    }

    private fun getDirection(view: View): Direction {
        return if (absoluteVelocity > minVelocity) {
            if (xVelocity >= 0) RIGHT else LEFT
        } else
            if (view.translationX >= 0) RIGHT else LEFT
    }

    private fun getTranslateXBy(view: View, direction: Direction): Float {
        val finalTranslationX: Float =
                ((parentWidth / 2 + view.getPerpendicularDistanceToCorner(finalRotation)) *
                        if (direction == LEFT) -1 else 1).toFloat()
        return finalTranslationX - view.translationX
    }

    private fun getRotation(dragState: DragState, direction: Direction): Float {
        val isClockwise = (dragState.isPickedUpFromTop).xor(direction == LEFT)
        return (finalRotation * if (isClockwise) 1 else -1).toFloat()
    }

    private fun getFlingDuration(translationXBy: Float): Long {
        val xVelocity = max(abs(xVelocity), minVelocity)
        return abs(translationXBy / xVelocity * 1000).toLong()
    }

    private fun getTranslationYBy(flingDuration: Long): Float {
        val yVelocity = minAbs(yVelocity, maxVelocity)
        return yVelocity * flingDuration / 1000
    }

    private val absoluteVelocity: Float
        get() = hypot(xVelocity, yVelocity)

    private val xVelocity: Float
        get() = velocityTracker?.xVelocity ?: 0f

    private val yVelocity: Float
        get() = velocityTracker?.yVelocity ?: 0f

    fun recycle() {
        velocityTracker?.recycle()
        velocityTracker = null
    }
}