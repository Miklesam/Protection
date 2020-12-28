package com.miklesam.pota

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Spell(var context: Context, player: Player) : GameObject(player.positionX, player.positionY) {

    private val paint: Paint = Paint()

    init {
        icon = BitmapFactory.decodeResource(context.resources, R.drawable.king1)
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
        velocityX = player.directionX * MAX_SPEED
        velocityY = player.directionY * MAX_SPEED
    }

    override fun draw(canvas: Canvas) {
        icon?.let { canvas.drawBitmap(it, positionX.toFloat(), positionY.toFloat(), paint) }
    }

    override fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        const val SPEED_PIXELS_PER_SECONDS = 800.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECONDS / GameLoop.UPS_MAX
    }

}
