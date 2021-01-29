package com.miklesam.pota

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
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
    var right = true

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
        right = joystic.acturatorX >= 0
        Log.d("Playyer", "${joystic.acturatorX}")

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
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king1)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king1_left)
                }
            }
            1 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king2)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king2_left)
                }
            }
            2 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king3)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king3_left)
                }
            }
            3 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king4)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king4_left)
                }
            }
            4 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king5)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king5_left)
                }
            }
            5 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king6)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king6_left)
                }
            }
            6 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king7)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king7_left)
                }
            }
            7 -> {
                icon = if (right) {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king8)
                } else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.king8_left)
                }
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
