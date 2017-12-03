package com.pcsalt.example.viewflipperdemo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper

class MainActivity : AppCompatActivity() {

    lateinit var vf: ViewFlipper
    lateinit var llIndicator: LinearLayout
    lateinit var bgActive: Drawable
    lateinit var bgInactive: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bgActive = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_indicator_active)
        bgInactive = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_indicator_inactive)

        vf = findViewById(R.id.view_flipper)
        llIndicator = findViewById(R.id.ll_indicator)

        generateFlipperView()
        generateIndicator()

        vf.inAnimation = AnimationUtils.loadAnimation(this, R.anim.in_from_right)
        vf.outAnimation = AnimationUtils.loadAnimation(this, R.anim.out_from_left)

        val animationListener = object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val position: Int = vf.getChildAt(vf.displayedChild).tag as Int
                var posInactive: Int
                if (position == 0) {
                    posInactive = vf.childCount - 1;
                } else {
                    posInactive = position - 1;
                }

                val childInactive = (llIndicator.getChildAt(posInactive) as FrameLayout).getChildAt(0)
                childInactive.background = bgInactive

                val childActive = (llIndicator.getChildAt(position) as FrameLayout).getChildAt(0)
                childActive.background = bgActive
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        }

        vf.inAnimation.setAnimationListener(animationListener)

        vf.isAutoStart = true
        vf.setFlipInterval(3000)
        vf.startFlipping()

    }

    private fun generateFlipperView() {
        val from = LayoutInflater.from(this)
        for (i in 0..6) {
            val view = from.inflate(R.layout.layout_vf_item, null)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            tvTitle.append("" + i)
            view.tag = i
            vf.addView(view)
        }
    }

    private fun generateIndicator() {
        val from = LayoutInflater.from(this)
        for (i in 0..6) {
            val view = from.inflate(R.layout.layout_indicator, null)
            val viewIndicator = view.findViewById<View>(R.id.view_indicator)
            when (i) {
                0 -> viewIndicator.background = bgActive
                else -> viewIndicator.background = bgInactive
            }
            llIndicator.addView(view)
        }
    }
}
