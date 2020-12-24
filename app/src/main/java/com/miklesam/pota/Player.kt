package com.miklesam.pota

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class Player(
    var context: Context,
    var positionX: Double,
    var positionY: Double
) {
    private val paint: Paint = Paint()
    private var mDrawable: Drawable? = null
    private var icon: Bitmap? = null
    private var velocityX = 0.0
    private var velocityY = 0.0

    init {
        icon = BitmapFactory.decodeResource(context.resources, R.drawable.king1)
        val color = ContextCompat.getColor(context, R.color.white)
        paint.setColor(color)
    }

    fun draw(canvas: Canvas) {
        icon?.let { canvas.drawBitmap(it, positionX.toFloat(), positionY.toFloat(), paint) }
    }

    fun update(joystic: Joystick) {
        velocityX = joystic.acturatorX * Companion.MAX_SPEED
        velocityY = joystic.acturatorY * Companion.MAX_SPEED
        positionX += velocityX
        positionY += velocityY
    }

    fun setPosition(
        positionX: Double,
        positionY: Double
    ) {
        this.positionX = positionX
        this.positionY = positionY
    }

    companion object {
        private const val  SPEED_PIXELS_PER_SECONDS = 400.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECONDS / GameLoop.UPS_MAX
    }

}
