package com.miklesam.pota

import kotlin.math.pow
import kotlin.math.sqrt

class Utils {
    companion object {
        fun getDistanceBetweenPoints(p1x: Double, p1y: Double, p2x: Double, p2y: Double): Double {
            return sqrt(
                (p1x - p2x).pow(2) +
                        (p1y - p2y).pow(2)
            )
        }
    }
}