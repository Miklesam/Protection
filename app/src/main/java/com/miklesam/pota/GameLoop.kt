package com.miklesam.pota

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(
    var game: Game,
    var surfaceHolder: SurfaceHolder
) : Thread() {
    private var isRunning = false

    var averageUPS = 0.0
    var averageFPS = 0.0
    fun startLoop() {
        isRunning = true
        start()
    }


    override fun run() {
        super.run()
        var canvas: Canvas? = null
        var updateCount = 0
        var frameCount = 0
        var startTime = 0L
        var elapsedTime = 0L
        var sleepTime = 0L

        startTime = System.currentTimeMillis()

        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.updateGame()
                    updateCount++
                    game.draw(canvas)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                sleep(sleepTime)
            }

            while (sleepTime < 0 && updateCount < UPS_MAX - 1) {
                game.updateGame()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()

            }

            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime)
                averageFPS = frameCount / (1E-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }

    companion object {
        const val UPS_MAX = 60.0
        const val UPS_PERIOD = 1E+3 / UPS_MAX
    }

}