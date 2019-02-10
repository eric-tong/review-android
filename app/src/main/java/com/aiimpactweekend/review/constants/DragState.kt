package com.aiimpactweekend.review.constants

@Suppress("ClassName")
sealed class DragState {
    object INITIALIZED : DragState()
    object READY : DragState()
    object BOUNCING_BACK : DragState()
    object FLINGING : DragState()
    object RECYCLED : DragState()
    object IGNORE : DragState()

    data class PICKED_UP(val position: Int) : DragState()
}

const val POSITION_TOP = 0
const val POSITION_BOTTOM = 1

val DragState.isDraggable: Boolean
    get() = isPickedUp || this == DragState.READY

val DragState.isPickedUp: Boolean
    get() = this is DragState.PICKED_UP

val DragState.isPickedUpFromTop: Boolean
    get() = this is DragState.PICKED_UP && position == POSITION_TOP

val DragState.isPickedUpFromBottom: Boolean
    get() = this is DragState.PICKED_UP && position == POSITION_BOTTOM
