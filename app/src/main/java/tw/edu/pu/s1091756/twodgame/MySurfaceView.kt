package tw.edu.pu.s1091756.twodgame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback{
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG:Bitmap
    lateinit var SuperMan: Bitmap
    var BGmoveX:Int = 0
    var xPos:Int = 0
    var yPos:Int = 0
    var deltaX:Int = 5
    var deltaY:Int = 5



    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.back)
        SuperMan = BitmapFactory.decodeResource(getResources(), R.drawable.superman)
        surfaceHolder.addCallback(this)}

    override fun surfaceCreated(holder: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }
    fun drawSomething(canvas:Canvas) {
        //canvas.drawBitmap(BG, 0f, 0f, null)
        //背景捲動
        BGmoveX -= 10
        var BGnewX:Int = BG.width + BGmoveX

        // 如果已捲動整張圖，則重新開始
        if (BGnewX <= 0) {
            BGmoveX = 0
            // only need one draw
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
        }else{
            // need to draw original and wrap
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
            canvas.drawBitmap(BG, BGnewX.toFloat(), 0f, null)
        }

        //超人反彈
        var SrcRect: Rect = Rect(0, 0, SuperMan.width, SuperMan.height) //裁切
        var w:Int = SuperMan.width / 6
        var h:Int = SuperMan.height / 6
        xPos += deltaX
        yPos += deltaY
        if (xPos >= getWidth()-w || xPos<=0){
            deltaX*=-1
        }
        if (yPos >= getHeight()-h || yPos<=0){
            deltaY*=-1
        }

        //var DestRect:Rect = Rect(0, 0, w, h)
        var DestRect:Rect = Rect(xPos, yPos, w + xPos, h + yPos)
        canvas.drawBitmap(SuperMan, SrcRect, DestRect, null)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}