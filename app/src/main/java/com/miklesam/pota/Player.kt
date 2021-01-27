package com.miklesam.pota

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.core.content.ContextCompat

class Player(
    var context: Context,
    positionX: Double,
    positionY: Double,
    var joystic: Joystick
) :
    GameObject(positionX, positionY) {

    private val paint: Paint = Paint()

    var screenWidth = 0
    var screenHeight = 0

    init {
        icon = BitmapFactory.decodeResource(context.resources, R.drawable.king1)
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
    }

    override fun draw(canvas: Canvas) {
        icon?.let { canvas.drawBitmap(it, positionX.toFloat(), positionY.toFloat(), paint) }
    }

    override fun update() {
        velocityX = joystic.acturatorX * MAX_SPEED
        velocityY = joystic.acturatorY * MAX_SPEED

        if (positionX + velocityX < 95 * screenWidth / 100 && positionX + velocityX > 0) {
            positionX += velocityX
        }
        if (positionY + velocityY < 90 * screenHeight / 100 && positionY + velocityY > 0) {
            positionY += velocityY
        }

        if (velocityX != 0.0 || velocityY != 0.0) {
            val distance = Utils.getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }
    }

    fun stop() {
        count = 0
        preCount = 0
        icon = BitmapFactory.decodeResource(context.resources, R.drawable.king1)
    }

    fun run() {
        when (count) {
            0 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king1)
            }
            1 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king2)
            }
            2 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king3)
            }
            3 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king4)
            }
            4 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king5)
            }
            5 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king6)
            }
            6 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king7)
            }
            7 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.king8)
            }
        }
        if (preCount < 2) {
            preCount++
        } else {
            preCount = 0
            if (count < 7) {
                count++
            } else {
                count = 0
            }
        }
    }

    companion object {
        const val SPEED_PIXELS_PER_SECONDS = 400.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECONDS / GameLoop.UPS_MAX
    }

}
