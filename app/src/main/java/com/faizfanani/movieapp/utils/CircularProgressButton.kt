package com.faizfanani.movieapp.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import com.faizfanani.movieapp.R
import com.google.android.material.button.MaterialButton

/**
 * Created by Moh.Faiz Fanani on 22/03/2023
 */
class CircularProgressButton : MaterialButton {

    private var isMorphingInProgress = false
    private var initialWidth = 0
    private var initialHeight = 0
    private var initialText = this.text.toString()

    constructor(context: Context) : super(context) {
        implementAttributes(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        implementAttributes(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        implementAttributes(attrs, defStyleAttr)
    }

    private fun implementAttributes(attrs: AttributeSet?, defStyleAttr: Int){
        inflate(context, R.layout.circular_progress_button, null)
    }

    fun progressingAnimation() {
        initialWidth = this.width
        initialHeight = this.height

        val widthAnimation = ValueAnimator.ofInt(initialWidth, initialHeight)
        widthAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = `val`
            setLayoutParams(layoutParams)
        }

        val morphingAnimatorSet = AnimatorSet()
        morphingAnimatorSet.duration = 500
        morphingAnimatorSet.play(widthAnimation)
        morphingAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                onAnimationState()
            }
        })
        morphingAnimatorSet.start()
    }

    fun reverseAnimation() {
        val widthAnimation = ValueAnimator.ofInt(initialHeight, initialWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = layoutParams
            layoutParams.width = `val`
            setLayoutParams(layoutParams)
        }

        val morphingAnimatorSet = AnimatorSet()
        morphingAnimatorSet.duration = 500
        morphingAnimatorSet.play(widthAnimation)
        morphingAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                resetState()
            }
        })
        morphingAnimatorSet.start()
    }

    private fun onAnimationState() {
        isMorphingInProgress = true
        this.text = ""
        this.isClickable = false
    }

    private fun resetState() {
        isMorphingInProgress = false
        this.text = initialText
        this.isClickable = true
        this.width = initialWidth
        this.height = initialHeight
    }
}