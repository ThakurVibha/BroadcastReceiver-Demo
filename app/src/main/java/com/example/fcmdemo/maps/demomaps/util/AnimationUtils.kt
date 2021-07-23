package com.example.fcmdemo.maps.demomaps.util

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

object AnimationUtils {
    fun polylineAnimator(): ValueAnimator {
        val valueAnimator = ValueAnimator.ofInt(0, 100)
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 4000
        return valueAnimator
    }
    fun carAnimator():ValueAnimator{
        val carAnimator=ValueAnimator.ofFloat(0f, 1f)
        carAnimator.duration=3000
        carAnimator.interpolator=LinearInterpolator()
        return carAnimator
    }
}
