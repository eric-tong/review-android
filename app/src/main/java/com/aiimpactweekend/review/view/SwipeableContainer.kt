package com.aiimpactweekend.review.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.aiimpactweekend.review.R
import com.aiimpactweekend.review.adapter.CardAdapter
import com.aiimpactweekend.review.animator.FlingAnimator
import com.aiimpactweekend.review.animator.bounceBack
import com.aiimpactweekend.review.animator.scaleTo
import com.aiimpactweekend.review.constants.*
import com.aiimpactweekend.review.constants.DragState.*
import com.aiimpactweekend.review.listener.SwipeListener
import com.aiimpactweekend.review.util.*
import timber.log.Timber
import java.util.*

class SwipeableContainer @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), View.OnTouchListener {
    private val flingAnimator = FlingAnimator(this)
    private val finalRotation = context.resources.getInteger(R.integer.card_rotation)
    private val backViewScale = context.resources.getInteger(R.integer.card_stack_scale) / 100f
    private var activeIndex = -1

    private val contentIndices = SparseIntArray()
    private val dragStates = SparseArray<DragState>()
    private val backViews = SparseArray<View>()

    private val lastTouchCoords = floatArrayOf(0f, 0f)
    private val recycledViews = ArrayList<View>()

    var swipeListener: SwipeListener? = null
    var adapter: CardAdapter? = null

    init {
        setOnTouchListener(this)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        Timber.d("ID %d\tAdd to parent", child.id)
        Timber.d("Total view count is %d", recycledViews.size + childCount)

        super.addView(child, index, params)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> onActionDown()
            MotionEvent.ACTION_MOVE -> onActionMove(event)
            MotionEvent.ACTION_UP -> onActionUp()
        }

        record(event)
        return true
    }

    private fun onActionDown() {
        Timber.d("========================")
        for (i in activeIndex until childCount) {
            val child = getChildAt(i)
            Timber.d("ID %d\t%s",
                    child.id, child.dragState)
        }
    }

    private fun onActionMove(event: MotionEvent) {
        flingAnimator.trackMotion(event)

        for (child in readyChildren) {
            child.updateDragState(event)
            if (child.dragState.isPickedUp && event.actionMasked == MotionEvent.ACTION_MOVE)
                child.drag(event)
        }
    }

    private val readyChildren: List<View>
        get() {
            val readyChildren = ArrayList<View>()
            for (i in activeIndex until childCount) {
                val child = getChildAt(i)
                if (child.dragState.isDraggable)
                    readyChildren.add(child)
            }
            return readyChildren
        }

    private fun View.updateDragState(event: MotionEvent) {
        if (shouldPickUp(event)) {
            pickUp(event)
        }
    }

    private fun View.shouldPickUp(event: MotionEvent) =
            dragState == READY
                    && !dragState.isPickedUp
                    && event.isWithin(bounds)
                    && isAdjacentTo(pickedUpChildren)

    private fun View.pickUp(event: MotionEvent) {
        Timber.d("ID %d\tPick up", id)

        dragState = getDragState(event)
        updateBackView()
    }

    private fun View.drag(event: MotionEvent) {
        val dragX = event.rawX - lastTouchCoords[0]
        val dragY = event.rawY - lastTouchCoords[1]
        translateBy(dragX, dragY)
        updateRotation(dragX)
    }

    private fun View.updateRotation(dragX: Float) {
        val proportion = dragX / this@SwipeableContainer.width
        rotation += proportion * finalRotation * if (dragState.isPickedUpFromBottom) -1 else 1
    }

    private fun onActionUp() {
        val pickedUpChildren = pickedUpChildren
        if (shouldFling(pickedUpChildren)) {
            fling(pickedUpChildren)
        } else {
            pickedUpChildren.putDownAll()
        }
        flingAnimator.recycle()
    }

    private fun shouldFling(views: List<View>) =
            isFlingEnabled(views.size) && views.any { flingAnimator.shouldFling(it) }

    private fun fling(views: List<View>) {
        val contentIndices = views.contentIndices
        val successfulFling = swipeListener?.isFlingSuccessful(contentIndices)
        views.flingAll(successfulFling ?: true)
        flingAnimator.resetTracker()
        swipeListener?.onFling(if (views[0].translationX < 0) Direction.LEFT else Direction.RIGHT)
    }

    private val pickedUpChildren: List<View>
        get() {
            val pickedUpChildren = ArrayList<View>()
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child.dragState.isPickedUp)
                    pickedUpChildren.add(child)
            }
            return pickedUpChildren
        }

    private fun isFlingEnabled(size: Int): Boolean {
        return size == 1 && swipeListener?.isMonoFlingEnabled() ?: false ||
                size > 1 && swipeListener?.isMultiFlingEnabled() ?: false
    }

    private fun List<View>.flingAll(success: Boolean = true) {
        forEach {
            it.fling(success)
            it.backView.scaleTo(1f)
        }
    }

    private fun View.fling(success: Boolean = true) {
        Timber.d("ID %d\tFling", id)

        flingAnimator.fling(this, dragState)
                .setListener(flingListener(this, success))
                .start()
    }

    private fun List<View>.putDownAll() {
        forEach { it.putDown() }
    }

    private fun View.putDown() {
        Timber.d("ID %d\tPut down", id)

        if (translationX != 0f || translationY != 0f) {
            Timber.d("ID %d\tBounce back", id)

            bounceBack().setListener(bounceBackListener(this))
            backView.scaleTo(backViewScale)
        }
    }

    private fun View.updateBackView() {
        if (!hasBackView)
            addBackView()
    }

    private val View.hasBackView: Boolean
        get() = backViews.indexOfKey(id) > -1

    private fun View.addBackView() {
        Timber.d("ID %d\tAdd backView", id)

        backView = getBackView()
        addView(backView, activeIndex)

        backView.reset(backViewScale)
        backView.layoutParams = layoutParams
        backView.contentIndex = contentIndex
        backView.dragState = INITIALIZED

        backView.loadNext()
    }

    private fun getBackView(): View {
        return if (recycledViews.isEmpty()) {
            Timber.d("ID %d\tInflating view", id)

            val backView = CardView(context)
            backView.id = View.generateViewId()
            backView
        } else {
            recycledViews.shift()
        }
    }

    private fun View.recycle() {
        Timber.d("ID %d\tRecycle", id)

        dragState = RECYCLED
        backViews.remove(id)
        recycledViews.add(this)
        removeView(this)
    }

    private var View.dragState: DragState
        get() = dragStates[id]!!
        set(value) {
            Timber.d("ID %d\tSet drag state\t%s", id, value)
            dragStates.append(id, value)
        }

    private var View.backView: View
        get() = backViews[id]!!
        set(value) {
            backViews.append(id, value)
        }

    private var View.contentIndex: Int
        get() = contentIndices[id]
        set(value) {
            Timber.d("ID %d\tSet content index\t%s", id, value)
            contentIndices.append(id, value)
        }

    private val List<View>.contentIndices: IntArray
        get() = map { it.contentIndex }.toIntArray()

    private fun flingListener(view: View, success: Boolean): AnimatorListenerAdapter {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.dragState = FLINGING
                if (success) view.backView.dragState = READY
                else swipeListener?.onUnsuccessfulFling()
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (success) {
                    view.recycle()
                    view.animate().setListener(null)
                } else {
                    view.putDown()
                }
            }
        }
    }

    private fun bounceBackListener(view: View): AnimatorListenerAdapter {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.dragState = BOUNCING_BACK
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.dragState = READY
                view.backView.recycle()
                backViews.remove(view.id)
                view.animate().setListener(null)
            }
        }
    }

    private fun record(event: MotionEvent) {
        lastTouchCoords[0] = event.rawX
        lastTouchCoords[1] = event.rawY
    }

    fun fling(vararg indices: Int) {
        val viewsToFling = ArrayList<View>()
        readyChildren.forEach {
            if (indices.contains(it.contentIndex)) viewsToFling.add(it)
        }
        viewsToFling.forEach {
            it.pickUp(MotionEvent.obtain(0,0,0,0F,0F,0))
        }
        fling(viewsToFling)
    }

    fun ready() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is CardView) {
                child.dragState = READY
                child.contentIndex = contentIndices.size()
                child.loadCurrent()

                if (activeIndex < 0) activeIndex = i
            } else
                child.dragState = IGNORE
        }
    }

    fun reset() {
        readyChildren.forEach {
            it.updateBackView()
            it.backView.loadCurrent()
        }
        readyChildren.flingAll()
    }

    private fun View.loadCurrent() {
        adapter?.loadCurrent(this, contentIndex)
    }

    private fun View.loadNext() {
        adapter?.loadNext(this, contentIndex)
    }
}
