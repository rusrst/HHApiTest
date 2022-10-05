package com.example.foundation.utils

import android.view.View

data class Size(val width: Int, val height: Int)

fun View.getMeasureSize(parent: View): Size {
    measure(
        View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
    )
    return Size(measuredWidth, measuredHeight)
}