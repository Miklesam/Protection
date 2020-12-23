package com.miklesam.pota

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import java.util.*


/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game(context: Context?) : SurfaceView(context),
    SurfaceHolder.Callback {

    var gameLoop: GameLoop
    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop.startLoop()
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {
        Log.d("Game.java", "surfaceChanged()")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawFPS(canvas)
        drawUPS(canvas)
    }

    fun updateGame() {

    }


    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)
        isFocusable = true
    }

    fun drawUPS(canvas: Canvas) {
        val averageUPS = gameLoop.getAverageUPS()
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
        paint.textSize = 100F
        canvas.drawText("UPS: $averageUPS", 400F, 300F, paint)
    }

    fun drawFPS(canvas: Canvas) {
        val averageFPS = gameLoop.getAverageFPS()
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
        paint.textSize = 100F
        canvas.drawText("FPS: $averageFPS", 200F, 400F, paint)
    }

}