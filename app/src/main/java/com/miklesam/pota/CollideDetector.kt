package com.miklesam.pota

import android.graphics.Rect
import android.util.Log

class CollideDetector {


    fun preCollide(
        player: Player,
        creeps: ArrayList<Creep>
    ): Boolean {

        val velocityX = player.joystic.acturatorX * Player.MAX_SPEED
        val velocityY = player.joystic.acturatorY * Player.MAX_SPEED


        val futureRect =
            Rect(
                player.rect.left + velocityX.toInt(),
                player.rect.top + velocityY.toInt(),
                player.rect.right + velocityX.toInt(),
                player.rect.bottom + velocityY.toInt()
            )

        //Log.d("Contaiiins", "future ${futureRect} ")

        return futureRect.intersects(
            creeps[0].rect.left,
            creeps[0].rect.top,
            creeps[0].rect.right,
            creeps[0].rect.bottom
        )
    }
}