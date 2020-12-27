package com.miklesam.pota

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game(context: Context?) : SurfaceView(context),
    SurfaceHolder.Callback {

    private var gameLoop: GameLoop
    private var player: Player
    private var joystick: Joystick
    private var creeps: ArrayList<Creep>

    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)
        joystick = Joystick(275, 700, 70, 40)
        player = Player(getContext(), 500.0, 500.0, joystick)
        creeps = ArrayList<Creep>()
        isFocusable = true
    }

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    joystick.IsPressedVal = true
                    player.run()
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (joystick.IsPressedVal) {
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                    player.run()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                joystick.IsPressedVal = false
                joystick.resetActuator()
                player.stop()
                return true
            }

        }
        return super.onTouchEvent(event)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawFPS(canvas)
        drawUPS(canvas)
        joystick.draw(canvas)
        player.draw(canvas)
        creeps.forEach {
            it.draw(canvas)
        }
    }

    fun drawUPS(canvas: Canvas) {
        val averageUPS = gameLoop.averageUPS
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
        paint.textSize = 50F
        canvas.drawText("UPS: $averageUPS", 100F, 100F, paint)
    }

    fun drawFPS(canvas: Canvas) {
        val averageFPS = gameLoop.averageFPS
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.white)
        paint.color = color
        paint.textSize = 50F
        canvas.drawText("FPS: $averageFPS", 100F, 200F, paint)
    }

    fun updateGame() {
        joystick.update()
        player.update()
        if (Creep.readyToSpawn()) {
            creeps.add(Creep(context, player = player))
        }
        creeps.forEach {
            it.update()
        }

        val iterator = creeps.iterator()
        while (iterator.hasNext()) {
            if (GameObject.isColliding(iterator.next(), player)) {
                iterator.remove()
            }
        }

    }

}