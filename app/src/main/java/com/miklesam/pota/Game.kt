package com.miklesam.pota

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat


/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screenPerform Attack
 */
class Game(context: Context?) : SurfaceView(context),
    SurfaceHolder.Callback {

    private var gameLoop: GameLoop
    private var player: Player
    private var joystick: Joystick

    private var creeps: ArrayList<Creep>


    //private var spells: ArrayList<Spell>
    private var joystickPointerId = 0
    private var iconBG: Bitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.bg)
    //private var numberOfSpellsToCast = 0

    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        gameLoop = GameLoop(this, surfaceHolder)
        joystick = Joystick(275, 700, 70, 40)

        creeps = ArrayList<Creep>()
        player = Player(getContext(), 500.0, 500.0, joystick)
        val playerRect = Rect(500, 500, 500 + player.icon!!.width, 500 + player.icon!!.height)
        player.rect = playerRect
        val creep = Creep(context!!)
        val creepRect = Rect(900, 500, 900 + creep.icon!!.width, 500 + creep.icon!!.height)

        creep.rect = creepRect
        creeps.add(creep)
        //spells = ArrayList<Spell>()
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
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.IsPressedVal) {
                    //numberOfSpellsToCast++
                    Log.d("Touch_attack", "onTouchEvent: Perform Attack2")
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.IsPressedVal = true
                    player.run()
                } else {
                    //numberOfSpellsToCast++
                    player.attack()
                    Log.d("Touch_attack", "onTouchEvent: Down")
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
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystickPointerId == event.getPointerId(event.actionIndex)) {
                    joystick.IsPressedVal = false
                    joystick.resetActuator()
                    player.stop()
                    player.attcEnd()
                    Log.d("Touch_attack", "onTouchEvent: Up")
                }
                return true
            }

        }
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        player.screenWidth = width
        player.screenHeight = height
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(iconBG,0f,0f,null)
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
        if (CollideDetector().preCollide(player, creeps)) {
            Log.d("Contaiiins", "contains ")
            //player.undoLast()

        } else {
            player.update()
        }
        /*creeps.forEach {
            it.update()
        }*/
        /*
    while (numberOfSpellsToCast > 0) {
        spells.add(Spell(context, player))
        numberOfSpellsToCast--
    }
    spells.forEach {
        it.update()
    }

    val iterator = creeps.iterator()
    while (iterator.hasNext()) {
        val enemy = iterator.next()
        if (GameObject.isColliding(enemy, player)) {
            iterator.remove()
            continue
        }
        val iteratorSpell = spells.iterator()
        while (iteratorSpell.hasNext()) {
            if (GameObject.isColliding(iteratorSpell.next(), enemy)) {
                iteratorSpell.remove()
                iterator.remove()

            }
        }
    }*/

    }

}