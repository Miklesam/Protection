package com.miklesam.pota

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.math.pow
import kotlin.math.sqrt

abstract class GameObject(
    var positionX: Double,
    var positionY: Double
) {
    protected var velocityX = 0.0
    protected var velocityY = 0.0

    var directionX = 0.0
    var directionY = 0.0

    var count = 0
    var preCount = 0

    var icon: Bitmap? = null

    abstract fun draw(canvas: Canvas)
    abstract fun update()


    companion object {
        fun getDistanceBeetwenObjects(obj1: GameObject, obj2: GameObject): Double {
            return sqrt(
                (obj2.positionX - obj1.positionX).pow(2) + (obj2.positionY - obj1.positionY).pow(
                    2
                )
            )
        }

        fun isColliding(it: GameObject, player: GameObject): Boolean {
            val distance = getDistanceBeetwenObjects(it, player)
            val distanceToCollision = player.icon!!.width
            return (distance < distanceToCollision)

        }
    }

}