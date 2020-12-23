package com.miklesam.pota

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(var game: Game, var surfaceHolder: SurfaceHolder) : Thread() {
    private var isRunning = false

    fun getAverageUPS(): Double {
        return 0.0
    }

    fun getAverageFPS(): Double {
        return 0.0
    }

    fun startLoop() {
        isRunning = true
        start()
    }


    override fun run() {
        super.run()
        var canvas: Canvas
        while (isRunning) {
            canvas = surfaceHolder.lockCanvas()
            game.updateGame()
            game.draw(canvas)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}