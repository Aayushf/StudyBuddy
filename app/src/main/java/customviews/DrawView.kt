package customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by aayushf on 19/8/17.
 */
class DrawView(c:Context, attr:AttributeSet): View(c, attr) {
    var drawPath: Path =  Path()
    var drawPaint = Paint()
    var canvasPaint = Paint()
    var drawCanvas = Canvas()
    var paintColour = 0xFF03A9F4.toInt()
    set(value){
        drawPaint.color = value

    }
    var bgcolor = 0xFF263238.toInt()
    var canvasBitmap:Bitmap? = null
    private fun setupDrawing(){
        drawPaint.color = paintColour
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 7.5f
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)



    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        (canvasBitmap as Bitmap).eraseColor(bgcolor)
        drawCanvas = Canvas(canvasBitmap)
        setupDrawing()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var touchX = event.x
        var touchY =event.y
        when(event.action){
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {drawCanvas.drawPath(drawPath, drawPaint);drawPath.reset()}
            else -> return false

        }
        invalidate()
        return true





    }
    fun setEraseMode(){
        drawPaint.color = bgcolor
        drawPaint.strokeWidth = 7f
        drawPaint.style = Paint.Style.FILL

    }
    fun unSetEraseMode(){
        setupDrawing()

    }

}