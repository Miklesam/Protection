package com.miklesam.pota

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.pow
import kotlin.math.sqrt

class Joystick(
    centerPositionX: Int,
    centerPositionY: Int,
    outerCircleRadius: Int,
    innerCircleRadius: Int
) {

    var IsPressedVal: Boolean
    private var outerCircleCenterPositionX: Int
    private var outerCircleCenterPositionY: Int
    private var innerCircleCenterPositionX: Int
    private var innerCircleCenterPositionY: Int
    private var innerCircleRadius: Int
    private var outerCircleRadius: Int
    private var outerCirclePaint: Paint
    private var innerCirclePaint: Paint
    private var joystickCenterToTouchDistance = 0.0
    var acturatorX = 0.0
    var acturatorY = 0.0

    init {
        outerCircleCenterPositionX = centerPositionX
        outerCircleCenterPositionY = centerPositionY
        innerCircleCenterPositionX = centerPositionX
        innerCircleCenterPositionY = centerPositionY
        this.innerCircleRadius = innerCircleRadius
        this.outerCircleRadius = outerCircleRadius

        outerCirclePaint = Paint()
        outerCirclePaint.color = Color.GRAY
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        innerCirclePaint = Paint()
        innerCirclePaint.color = Color.BLUE
        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE
        IsPressedVal = false
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            outerCircleCenterPositionX.toFloat(),
            outerCircleCenterPositionY.toFloat(),
            outerCircleRadius.toFloat(),
            outerCirclePaint
        )

        canvas.drawCircle(
            innerCircleCenterPositionX.toFloat(),
            innerCircleCenterPositionY.toFloat(),
            innerCircleRadius.toFloat(),
            innerCirclePaint
        )
    }

    fun update() {
        updateInnerCirclePosition()
    }

    private fun updateInnerCirclePosition() {
        innerCircleCenterPositionX= (outerCircleCenterPositionX+acturatorX*outerCircleRadius).toInt()
        innerCircleCenterPositionY=(outerCircleCenterPositionY+acturatorY*outerCircleRadius).toInt()
    }

    fun isPressed(touchPositionX: Double, touchPositionY: Double): Boolean {
        joystickCenterToTouchDistance = sqrt(
            (outerCircleCenterPositionX - touchPositionX).pow(2.0) +
                    (outerCircleCenterPositionY - touchPositionY).pow(2.0)
        )
        return joystickCenterToTouchDistance < outerCircleRadius
    }

    fun setActuator(touchPositionX: Double, touchPositionY: Double) {
        val deltaX = touchPositionX - outerCircleCenterPositionX
        val deltaY = touchPositionY - outerCircleCenterPositionY
        val deltaDistance = sqrt(deltaX.pow(2.0) + deltaY.pow(2.0))

        if (deltaDistance < outerCircleRadius) {
            acturatorX = deltaX / outerCircleRadius
            acturatorY = deltaY / outerCircleRadius
        } else {
            acturatorX = deltaX / deltaDistance
            acturatorY = deltaY / deltaDistance
        }
    }

    fun resetActuator() {
        acturatorX = 0.0
        acturatorY = 0.0
    }

}
