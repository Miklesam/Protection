package com.miklesam.pota

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.core.content.ContextCompat

class DireHealthBar(val creep: Creep, val context: Context) {

    val margin: Int
    val height: Int
    val borderPaint: Paint
    val healthPaint: Paint

    init {
        height = 20
        margin = 2
        borderPaint = Paint()
        val borderColor = ContextCompat.getColor(context, R.color.healthBarDireBorder)
        borderPaint.color = borderColor


        healthPaint = Paint()
        val healthColor = ContextCompat.getColor(context, R.color.healthBarDireHealth)
        healthPaint.color = healthColor


    }

    fun draw(canvas: Canvas) {
        val x = creep.positionX
        val y = creep.positionY
        val healthPointPercentage = creep.hp / 250

        val borderLeft = x.toFloat() + 1 * creep.icon!!.width / 100
        val borderRight = x.toFloat() + 99f * creep.icon!!.width / 100
        val borderBottom = y.toFloat()
        val borderTop = y.toFloat() - 10f
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint)

        val healthWidth = borderRight - borderLeft
        val healthHeight = 10f

        val healthLeft = borderLeft
        val healt = healthWidth * healthPointPercentage
        val healthRight = borderLeft + healt
        val healthBottom = borderBottom
        val healthTop = borderBottom - healthHeight
        if (healt > 0) {
            canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, healthPaint)
        }


    }
}
