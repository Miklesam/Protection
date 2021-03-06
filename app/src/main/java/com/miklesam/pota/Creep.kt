package com.miklesam.pota

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat

class Creep(var context: Context, positionX: Double, positionY: Double) :
    GameObject(positionX, positionY) {
    constructor(context: Context) : this(context, 900.0, 500.0) {

    }
    private val healthBar: DireHealthBar
    private val paint: Paint = Paint()
    lateinit var rect: Rect
    var hp = 250f

    init {
        icon = BitmapFactory.decodeResource(context.resources, R.drawable.creep_small_1)
        val color = ContextCompat.getColor(context, R.color.white)
        this.healthBar = DireHealthBar(this, context)
        paint.color = color
    }


    override fun draw(canvas: Canvas) {
        icon?.let { canvas.drawBitmap(it, positionX.toFloat(), positionY.toFloat(), paint) }
        healthBar.draw(canvas)
    }

    override fun update() {
        run()
        /* val distanceToPlayerX = player.positionX - positionX
         val distanceToPlayerY = player.positionY - positionY

         val distanceToPlayer = getDistanceBeetwenObjects(this, player)

         val directionX = distanceToPlayerX / distanceToPlayer
         val directionY = distanceToPlayerY / distanceToPlayer

         if (distanceToPlayer > 0) {
             velocityX = directionX * MAX_SPEED
             velocityY = directionY * MAX_SPEED
         } else {
             velocityX = 0.0
             velocityY = 0.0
         }

         positionX += velocityX
         positionY += velocityY*/

    }

    fun run() {
        when (count) {
            0 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.creep_small_1)
            }
            1 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.creep_small_2)
            }
            2 -> {
                icon = BitmapFactory.decodeResource(context.resources, R.drawable.creep_small_3)
            }
        }
        if (preCount < 2) {
            preCount++
        } else {
            preCount = 0
            if (count < 2) {
                count++
            } else {
                count = 0
            }
        }
    }


    companion object {
        fun readyToSpawn(): Boolean {
            if (updatesUntilNextSpawns <= 0) {
                updatesUntilNextSpawns += UPDATES_PER_SPAWN
                return true
            } else {
                updatesUntilNextSpawns--
                return false
            }
        }

        private const val SPEED_PIXELS_PER_SECONDS = Player.SPEED_PIXELS_PER_SECONDS * 0.6
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECONDS / GameLoop.UPS_MAX
        private const val SPAWN_PER_MINUTE = 20.0
        private const val SPAWN_PER_SECOND = SPAWN_PER_MINUTE / 60.0
        private const val UPDATES_PER_SPAWN = GameLoop.UPS_MAX / SPAWN_PER_SECOND
        private var updatesUntilNextSpawns = UPDATES_PER_SPAWN

    }

}
