package com.sheldon.bujofe.studyroom

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.sheldon.bujofe.R
import java.util.*

/**
 * Created by baoyunlong on 16/6/16.
 */
class SeatTable : View {
    private val DBG = false
    private var paint = Paint()
    private var overviewPaint = Paint()
    private var lineNumberPaint: Paint? = null
    private var lineNumberTxtHeight: Float = 0.toFloat()

    /**
     * 用來保存所有行號
     */
    private var lineNumbers: ArrayList<String>? = ArrayList()

    private var lineNumberPaintFontMetrics: Paint.FontMetrics? = null
    internal var matrix = Matrix()

    /**
     * 座位水平間距
     */
    internal var spacing: Int = 0

    /**
     * 座位垂直間距
     */
    internal var verSpacing: Int = 0

    /**
     * 行號寬度
     */
    internal var numberWidth: Int = 0

    /**
     * 行數
     */
    internal var row: Int = 0

    /**
     * 列數
     */
    internal var column: Int = 0

    /**
     * 可選時座位的圖片
     */

    private var seatBitmap: Bitmap? = null
    /**
     * 選中時座位的圖片
     */
    private var checkedSeatBitmap: Bitmap? = null

    /**
     * 座位已經售出時的圖片
     */
    private var seatSoldBitmap: Bitmap? = null

    private var overviewBitmap: Bitmap? = null

    private var lastX: Int = 0
    private var lastY: Int = 0

    /**
     * 整個座位圖的寬度'
     */
    private var seatBitmapWidth: Int = 0

    /**
     * 整個座位圖的高度
     */
    private var seatBitmapHeight: Int = 0

    /**
     * 標示是否需要繪製座位圖
     */
    internal var isNeedDrawSeatBitmap = true

    /**
     * 概覽圖白色方塊高度
     */
    private var rectHeight: Float = 0.toFloat()

    /**
     * 概覽圖白色方塊寬度
     */
    private var rectWidth: Float = 0.toFloat()

    /**
     * 概覽圖上方塊的水平間距
     */
    private var overviewSpacing: Float = 0.toFloat()

    /**
     * 概覽圖上方塊的垂直間距
     */
    private var overviewVerSpacing: Float = 0.toFloat()

    /**
     * 概覽圖的比例
     */
    private var overviewScale = 4.8f

    /**
     * 螢幕高度
     */
    private var screenHeight: Float = 0.toFloat()

    /**
     * 螢幕默認寬度與座位圖的比例
     */
    private var screenWidthScale = 0.5f

    /**
     * 螢幕最小寬度
     */
    private var defaultScreenWidth: Int = 0

    /**
     * 標示是否正在縮放
     */
    internal var isScaling: Boolean = false
    internal var scaleX: Float = 0.toFloat()
    internal var scaleY: Float = 0.toFloat()

    /**
     * 是否是第一次縮放
     */
    internal var firstScale = true

    /**
     * 最多可以選擇的座位數量
     */
    internal var maxSelected = Integer.MAX_VALUE

    private var seatChecker: SeatChecker? = null

    /**
     * 螢幕名稱
     */
    private var screenName = ""

    /**
     * 整個概覽圖的寬度
     */
    private var rectW: Float = 0.toFloat()

    /**
     * 整個概覽圖的高度
     */
    private var rectH: Float = 0.toFloat()

    private var headPaint: Paint? = null
    private var headBitmap: Bitmap? = null

    /**
     * 是否第一次執行onDraw
     */
    internal var isFirstDraw = true

    /**
     * 標示是否需要繪製概覽圖
     */
    private var isDrawOverview = false

    /**
     * 標示是否需要更新概覽圖
     */
    internal var isDrawOverviewBitmap = true

    private var overview_checked: Int = 0
    private var overview_sold: Int = 0
    private var txt_color: Int = 0
    private var seatCheckedResID: Int = 0
    private var seatSoldResID: Int = 0
    private var seatAvailableResID: Int = 0

    internal var isOnClick: Boolean = false

    private var downX: Int = 0
    private var downY: Int = 0
    private var pointer: Boolean = false

    /**
     * 頂部高度,可選,已選,已售區域的高度
     */
    private var headHeight: Float = 0.toFloat()

    private var pathPaint: Paint? = null
    private var rectF: RectF? = null

    /**
     * 頭部下面横線的高度
     */
    private var borderHeight = 1
    private var redBorderPaint: Paint? = null

    /**
     * 默認的座位圖寬度,如果使用的自己的座位圖片比這個尺寸大或者小,會缩放到這個大小
     */
    private val defaultImgW = 65f

    /**
     * 默認的座位圖高度
     */
    private val defaultImgH = 55f

    /**
     * 座位圖片的寬度
     */
    private var seatWidth: Int = 10

    /**
     * 座位圖片的高度
     */
    private var seatHeight: Int = 10

    private var xScale1 = 10f
    private var yScale1 = 10f

    private val hideOverviewRunnable = Runnable {
        isDrawOverview = false
        invalidate()
    }

    private var tempMatrix = Matrix()

    private var bacColor = Color.parseColor("#7e000000")

    internal var handler = Handler()

    internal var selects = ArrayList<Int>()

    val selectedSeat: ArrayList<String>
        get() {
            val results = ArrayList<String>()
            for (i in 0 until this.row) {
                for (j in 0 until this.column) {
                    if (isHave(getID(i, j)) >= 0) {
                        results.add("$i,$j")
                    }
                }
            }
            return results
        }

    private var m = FloatArray(9)

    private val translateX: Float
        get() {
            matrix.getValues(m)
            return m[2]
        }

    private val translateY: Float
        get() {
            matrix.getValues(m)
            return m[5]
        }

    private val matrixScaleY: Float
        get() {
            matrix.getValues(m)
            return m[4]
        }

    private val matrixScaleX: Float
        get() {
            matrix.getValues(m)
            return m[Matrix.MSCALE_X]
        }

    private var zoom: Float = 0.toFloat()

    private var scaleGestureDetector =
        ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                isScaling = true
                var scaleFactor = detector.scaleFactor
                if (matrixScaleY * scaleFactor > 3) {
                    scaleFactor = 3 / matrixScaleY
                }
                if (firstScale) {
                    scaleX = detector.currentSpanX
                    scaleY = detector.currentSpanY
                    firstScale = false
                }

                if (matrixScaleY * scaleFactor < 0.5) {
                    scaleFactor = 0.5f / matrixScaleY
                }
                matrix.postScale(scaleFactor, scaleFactor, scaleX, scaleY)
                invalidate()
                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                isScaling = false
                firstScale = true
            }
        })

    private var gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                isOnClick = true
                val x = e.x.toInt()
                val y = e.y.toInt()

                for (i in 0 until row) {
                    for (j in 0 until column) {
                        val tempX =
                            ((j * seatWidth + (j + 1) * spacing) * matrixScaleX + translateX).toInt()
                        val maxTemX = (tempX + seatWidth * matrixScaleX).toInt()

                        val tempY =
                            ((i * seatHeight + i * verSpacing) * matrixScaleY + translateY).toInt()
                        val maxTempY = (tempY + seatHeight * matrixScaleY).toInt()

                        if (seatChecker != null && seatChecker!!.isValidSeat(
                                i,
                                j
                            ) && !seatChecker!!.isSold(i, j)
                        ) {
                            if (x in tempX..maxTemX && y >= tempY && y <= maxTempY) {
                                val id = getID(i, j)
                                val index = isHave(id)
                                if (index >= 0) {
                                    remove(index)
                                    if (seatChecker != null) {
                                        seatChecker!!.unCheck(i, j)
                                    }
                                } else {
                                    if (selects.size >= maxSelected) {
                                        Toast.makeText(
                                            context,
                                            "最多只能選取" + maxSelected + "個",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return super.onSingleTapConfirmed(e)
                                    } else {
                                        addChooseSeat(i, j)
                                        if (seatChecker != null) {
                                            seatChecker!!.checked(i, j)
                                        }
                                    }
                                }
                                isNeedDrawSeatBitmap = true
                                isDrawOverviewBitmap = true
                                val currentScaleY = matrixScaleY

                                if (currentScaleY < 1.7) {
                                    scaleX = x.toFloat()
                                    scaleY = y.toFloat()
                                    zoomAnimate(currentScaleY, 3f)//2f
                                }

                                invalidate()
                                break
                            }
                        }
                    }
                }

                return super.onSingleTapConfirmed(e)
            }
        })

    /**
     * 設置行號 默認顯示 1,2,3....數字
     * @param lineNumbers
     */
    fun setLineNumbers(lineNumbers: ArrayList<String>) {
        this.lineNumbers = lineNumbers
        invalidate()
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeatTableView)
        overview_checked = typedArray.getColor(
            R.styleable.SeatTableView_overview_checked,
            Color.parseColor("#5A9E64")
        )
        overview_sold = typedArray.getColor(R.styleable.SeatTableView_overview_sold, Color.RED)
        txt_color = typedArray.getColor(R.styleable.SeatTableView_txt_color, Color.WHITE)
        seatCheckedResID =
            typedArray.getResourceId(R.styleable.SeatTableView_seat_checked, R.drawable.seat_green)
        seatSoldResID =
            typedArray.getResourceId(R.styleable.SeatTableView_overview_sold, R.drawable.seat_sold)
        seatAvailableResID =
            typedArray.getResourceId(R.styleable.SeatTableView_seat_available, R.drawable.seat_gray)
        typedArray.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }


    private fun init() {

        spacing = dip2Px(10f).toInt()
        verSpacing = dip2Px(5f).toInt()
        defaultScreenWidth = dip2Px(120f).toInt()

        seatBitmap = BitmapFactory.decodeResource(resources, seatAvailableResID)

        val scaleX = defaultImgW / (seatBitmap?.width
            ?: throw NullPointerException("Expression 'seatBitmap?.width' must not be null"))

        val scaleY = defaultImgH / (seatBitmap?.height
            ?: throw NullPointerException("Expression 'seatBitmap?.height' must not be null"))

        xScale1 = scaleX
        yScale1 = scaleY

        seatHeight = (seatBitmap?.height?.times(yScale1))!!.toInt()
        seatWidth = (seatBitmap?.width?.times(xScale1))!!.toInt()

        checkedSeatBitmap = BitmapFactory.decodeResource(resources, seatCheckedResID)
        seatSoldBitmap = BitmapFactory.decodeResource(resources, seatSoldResID)

        seatBitmapWidth =
            (column.toFloat() * seatBitmap?.width!!.toFloat() * xScale1 + (column - 1) * spacing).toInt()
        seatBitmapHeight =
            (row.toFloat() * seatBitmap?.height!!.toFloat() * yScale1 + (row - 1) * verSpacing).toInt()
        paint.color = Color.RED
        numberWidth = dip2Px(20f).toInt()

        screenHeight = dip2Px(20f)
        headHeight = dip2Px(30f)

        headPaint = Paint()
        headPaint!!.style = Paint.Style.FILL
        headPaint!!.textSize = 24f
        headPaint!!.color = Color.WHITE
        headPaint!!.isAntiAlias = true

        pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pathPaint!!.style = Paint.Style.FILL
        pathPaint!!.color = Color.parseColor("#e2e2e2")

        redBorderPaint = Paint()
        redBorderPaint!!.isAntiAlias = true
        redBorderPaint!!.color = Color.RED
        redBorderPaint!!.style = Paint.Style.STROKE
        redBorderPaint!!.strokeWidth = resources.displayMetrics.density * 1

        rectF = RectF()

        rectHeight = seatHeight / overviewScale
        rectWidth = seatWidth / overviewScale
        overviewSpacing = spacing / overviewScale
        overviewVerSpacing = verSpacing / overviewScale

        rectW = column * rectWidth + (column - 1) * overviewSpacing + overviewSpacing * 2
        rectH = row * rectHeight + (row - 1) * overviewVerSpacing + overviewVerSpacing * 2
        overviewBitmap = Bitmap.createBitmap(rectW.toInt(), rectH.toInt(), Bitmap.Config.ARGB_8888)

        lineNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        lineNumberPaint!!.color = bacColor
        lineNumberPaint!!.textSize = resources.displayMetrics.density * 16
        lineNumberTxtHeight = lineNumberPaint!!.measureText("4")
        lineNumberPaintFontMetrics = lineNumberPaint!!.fontMetrics
        lineNumberPaint!!.textAlign = Paint.Align.CENTER

        if (lineNumbers == null) {
            lineNumbers = ArrayList()
        } else if (lineNumbers!!.size <= 0) {
            for (i in 0 until row) {
                lineNumbers!!.add((i + 1).toString() + "")
            }
        }


        matrix.postTranslate(
            (numberWidth + spacing).toFloat(),
            headHeight + screenHeight + borderHeight.toFloat() + verSpacing.toFloat()
        )

    }

    override fun onDraw(canvas: Canvas) {
        val startTime = System.currentTimeMillis()
        if (row <= 0 || column == 0) {
            return
        }

        /**
         * 座位顯示
         * */
        drawSeat(canvas)

        /**
         * 數字顯示
         * */
        drawNumber(canvas)

        /**
         * 螢幕顯示
         * */
        drawScreen(canvas)

        headBitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }


        if (isDrawOverview) {
            val s = System.currentTimeMillis()
            if (isDrawOverviewBitmap) {
                drawOverview()
            }
            overviewBitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }
            drawOverview(canvas)
            Log.d("drawTime", "OverviewDrawTime:" + (System.currentTimeMillis() - s))
        }

        if (DBG) {
            val drawTime = System.currentTimeMillis() - startTime
            Log.d("drawTime", "totalDrawTime:$drawTime")
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y = event.y.toInt()
        val x = event.x.toInt()
        super.onTouchEvent(event)

        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        val pointerCount = event.pointerCount
        if (pointerCount > 1) {
            pointer = true
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pointer = false
                downX = x
                downY = y
                isDrawOverview = true
                handler.removeCallbacks(hideOverviewRunnable)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> if (!isScaling && !isOnClick) {
                val downDX = Math.abs(x - downX)
                val downDY = Math.abs(y - downY)
                if ((downDX > 10 || downDY > 10) && !pointer) {
                    val dx = x - lastX
                    val dy = y - lastY
                    matrix.postTranslate(dx.toFloat(), dy.toFloat())
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                handler.postDelayed(hideOverviewRunnable, 1500)

                autoScale()
                val downDX = Math.abs(x - downX)
                val downDY = Math.abs(y - downY)
                if ((downDX > 10 || downDY > 10) && !pointer) {
                    autoScroll()
                }
            }
        }
        isOnClick = false
        lastY = y
        lastX = x

        return true
    }

    internal fun drawHeadInfo(): Bitmap {
        val txt = "已售"
        val txtY = headPaint?.let { getBaseLine(it, 0f, headHeight) }
        val txtWidth = headPaint?.measureText(txt)?.toInt()
        val spacing = dip2Px(10f)
        val spacing1 = dip2Px(5f)
        val y = (headHeight - seatBitmap?.height!!) / 2

        val width =
            seatBitmap?.width!!.toFloat() + spacing1 + txtWidth!!.toFloat() + spacing + seatSoldBitmap?.width!!.toFloat() + txtWidth.toFloat() + spacing1 + spacing + checkedSeatBitmap?.height!!.toFloat() + spacing1 + txtWidth.toFloat()
        val bitmap = Bitmap.createBitmap(getWidth(), headHeight.toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        //繪製背景
        headPaint?.let { canvas.drawRect(0f, 0f, getWidth().toFloat(), headHeight, it) }
        headPaint?.color = Color.BLACK

        val startX = (getWidth() - width) / 2
        tempMatrix.setScale(xScale1, yScale1)
        tempMatrix.postTranslate(startX, (headHeight - seatHeight) / 2)
        canvas.drawBitmap(seatBitmap!!, tempMatrix, headPaint)
        canvas.drawText("可選", startX + seatWidth.toFloat() + spacing1, txtY!!, headPaint!!)

        val soldSeatBitmapY =
            startX + seatBitmap!!.width.toFloat() + spacing1 + txtWidth.toFloat() + spacing
        tempMatrix.setScale(xScale1, yScale1)
        tempMatrix.postTranslate(soldSeatBitmapY, (headHeight - seatHeight) / 2)
        canvas.drawBitmap(seatSoldBitmap!!, tempMatrix, headPaint)
        canvas.drawText("已售", soldSeatBitmapY + seatWidth.toFloat() + spacing1, txtY, headPaint!!)

        val checkedSeatBitmapX =
            soldSeatBitmapY + seatSoldBitmap!!.width.toFloat() + spacing1 + txtWidth.toFloat() + spacing
        tempMatrix.setScale(xScale1, yScale1)
        tempMatrix.postTranslate(checkedSeatBitmapX, y)
        canvas.drawBitmap(checkedSeatBitmap!!, tempMatrix, headPaint)
        canvas.drawText(
            "已選", checkedSeatBitmapX + spacing1 + seatWidth.toFloat(), txtY,
            headPaint!!
        )

        //繪製分割線
        headPaint!!.strokeWidth = 1f
        headPaint!!.color = Color.GRAY
        canvas.drawLine(0f, headHeight, getWidth().toFloat(), headHeight, headPaint!!)
        return bitmap

    }

    /**
     * 繪製中間螢幕
     */
    private fun drawScreen(canvas: Canvas) {
        pathPaint?.style = Paint.Style.FILL
        pathPaint?.color = Color.parseColor("#e2e2e2")
        val startY = headHeight + borderHeight

        val centerX = seatBitmapWidth * matrixScaleX / 2 + translateX
        var screenWidth = seatBitmapWidth.toFloat() * screenWidthScale * matrixScaleX
        if (screenWidth < defaultScreenWidth) {
            screenWidth = defaultScreenWidth.toFloat()
        }

        val path = Path()
        path.moveTo(centerX, startY)
        path.lineTo(centerX - screenWidth / 2, startY)
        path.lineTo(centerX - screenWidth / 2 + 20, screenHeight * matrixScaleY + startY)
        path.lineTo(centerX + screenWidth / 2 - 20, screenHeight * matrixScaleY + startY)
        path.lineTo(centerX + screenWidth / 2, startY)

        pathPaint?.let { canvas.drawPath(path, it) }

        pathPaint?.color = Color.BLACK
        pathPaint?.textSize = 20 * matrixScaleX

        canvas.drawText(
            screenName,
            centerX - pathPaint?.measureText(screenName)!! / 2,
            getBaseLine(pathPaint!!, startY, startY + screenHeight * matrixScaleY),
            pathPaint!!
        )
    }

    private fun drawSeat(canvas: Canvas) {
        zoom = matrixScaleX
        val startTime = System.currentTimeMillis()
        val translateX = translateX
        val translateY = translateY
        val scaleX = zoom
        val scaleY = zoom

        for (i in 0 until row) {
            val top =
                i.toFloat() * seatBitmap!!.height.toFloat() * yScale1 * scaleY + i.toFloat() * verSpacing.toFloat() * scaleY + translateY

            val bottom = top + seatBitmap!!.height.toFloat() * yScale1 * scaleY
            if (bottom < 0 || top > height) {
                continue
            }

            for (j in 0 until column) {
                val left =
                    j.toFloat() * seatBitmap!!.width.toFloat() * xScale1 * scaleX + j.toFloat() * spacing.toFloat() * scaleX + translateX

                val right = left + seatBitmap!!.width.toFloat() * xScale1 * scaleY
                if (right < 0 || left > width) {
                    continue
                }

                val seatType = getSeatType(i, j)
                tempMatrix.setTranslate(left, top)
                tempMatrix.postScale(xScale1, yScale1, left, top)
                tempMatrix.postScale(scaleX, scaleY, left, top)

                when (seatType) {
                    SEAT_TYPE_AVAILABLE -> canvas.drawBitmap(seatBitmap!!, tempMatrix, paint)
                    SEAT_TYPE_NOT_AVAILABLE -> {
                    }
                    SEAT_TYPE_SELECTED -> {
                        canvas.drawBitmap(checkedSeatBitmap!!, tempMatrix, paint)
                        drawText(canvas, i, j, top, left)
                    }
                    SEAT_TYPE_SOLD -> canvas.drawBitmap(seatSoldBitmap!!, tempMatrix, paint)
                }

            }
        }

        if (DBG) {
            val drawTime = System.currentTimeMillis() - startTime
            Log.d("drawTime", "seatDrawTime:$drawTime")
        }
    }

    private fun getSeatType(row: Int, column: Int): Int {

        if (isHave(getID(row, column)) >= 0) {
            return SEAT_TYPE_SELECTED
        }

        if (seatChecker != null) {
            if (!seatChecker!!.isValidSeat(row, column)) {
                return SEAT_TYPE_NOT_AVAILABLE
            } else if (seatChecker!!.isSold(row, column)) {
                return SEAT_TYPE_SOLD
            }
        }

        return SEAT_TYPE_AVAILABLE
    }

    private fun getID(row: Int, column: Int): Int {
        return row * this.column + (column + 1)
    }

    /**
     * 繪製選中座位的行號列號
     *
     * @param row
     * @param column
     */
    private fun drawText(canvas: Canvas, row: Int, column: Int, top: Float, left: Float) {

        var txt = (row + 1).toString() + "排"
        var txt1: String? = (column + 1).toString() + "座"

        if (seatChecker != null) {
            val strings = seatChecker!!.checkedSeatTxt(row, column)
            if (strings != null && strings.size > 0) {
                if (strings.size >= 2) {
                    txt = strings[0]
                    txt1 = strings[1]
                } else {
                    txt = strings[0]
                    txt1 = null
                }
            }
        }

        val txtPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        txtPaint.color = txt_color
        txtPaint.typeface = Typeface.DEFAULT_BOLD
        val seatHeight = this.seatHeight * matrixScaleX
        val seatWidth = this.seatWidth * matrixScaleX
        txtPaint.textSize = seatHeight / 3

        //获取中间线
        val center = seatHeight / 2
        val txtWidth = txtPaint.measureText(txt)
        val startX = left + seatWidth / 2 - txtWidth / 2

        //只绘制一行文字
        if (txt1 == null) {
            canvas.drawText(txt, startX, getBaseLine(txtPaint, top, top + seatHeight), txtPaint)
        } else {
            canvas.drawText(txt, startX, getBaseLine(txtPaint, top, top + center), txtPaint)
            canvas.drawText(
                txt1,
                startX,
                getBaseLine(txtPaint, top + center, top + center + seatHeight / 2),
                txtPaint
            )
        }

        if (DBG) {
            Log.d("drawTest:", "top:$top")
        }
    }

    /**
     * 繪製行號
     */
    private fun drawNumber(canvas: Canvas) {
        val startTime = System.currentTimeMillis()
        lineNumberPaint!!.color = bacColor
        val translateY = translateY.toInt()
        val scaleY = matrixScaleY

        rectF?.top = translateY - lineNumberTxtHeight / 2
        rectF?.bottom = translateY.toFloat() + seatBitmapHeight * scaleY + lineNumberTxtHeight / 2
        rectF?.left = 0f
        rectF?.right = numberWidth.toFloat()
        canvas.drawRoundRect(
            rectF!!,
            (numberWidth / 2).toFloat(),
            (numberWidth / 2).toFloat(),
            lineNumberPaint!!
        )

        lineNumberPaint!!.color = Color.WHITE

        for (i in 0 until row) {

            val top = (i * seatHeight + i * verSpacing) * scaleY + translateY
            val bottom = (i * seatHeight + i * verSpacing + seatHeight) * scaleY + translateY
            val baseline =
                (bottom + top - lineNumberPaintFontMetrics!!.bottom - lineNumberPaintFontMetrics!!.top) / 2

            canvas.drawText(
                lineNumbers!![i],
                (numberWidth / 2).toFloat(),
                baseline,
                lineNumberPaint!!
            )
        }

        if (DBG) {
            val drawTime = System.currentTimeMillis() - startTime
            Log.d("drawTime", "drawNumberTime:$drawTime")
        }
    }

    /**
     * 繪製概覽圖
     */
    private fun drawOverview(canvas: Canvas) {

        //繪製红色框
        var left = (-translateX).toInt()
        if (left < 0) {
            left = 0
        }
        left /= overviewScale.toInt()

        Log.d("SeatTable","left = $left")
        Log.d("SeatTable","overviewScale = ${overviewScale.toInt()}")
        //left /= matrixScaleX.toInt() <- 不必要  但先留著
        Log.d("SeatTable","matrixScaleX = ${overviewScale.toInt()}")



        var currentWidth =
            (translateX + (column * seatWidth + spacing * (column - 1)) * matrixScaleX).toInt()
        if (currentWidth > width) {
            currentWidth -= width
        } else {
            currentWidth = 0
        }
        val right = (rectW - currentWidth.toFloat() / overviewScale / matrixScaleX).toInt()

        var top = -translateY + headHeight
        if (top < 0) {
            top = 0f
        }
        top /= overviewScale
        top /= matrixScaleY
        if (top > 0) {
            top += overviewVerSpacing
        }

        var currentHeight =
            (translateY + (row * seatHeight + verSpacing * (row - 1)) * matrixScaleY).toInt()
        if (currentHeight > height) {
            currentHeight = currentHeight - height
        } else {
            currentHeight = 0
        }
        val bottom = (rectH - currentHeight.toFloat() / overviewScale / matrixScaleY).toInt()

        redBorderPaint?.let {
            canvas.drawRect(
                left.toFloat(), top, right.toFloat(), bottom.toFloat(),
                it
            )
        }
    }

    private fun drawOverview(): Bitmap? {
        isDrawOverviewBitmap = false

        val bac = Color.parseColor("#7e000000")
        overviewPaint.color = bac
        overviewPaint.isAntiAlias = true
        overviewPaint.style = Paint.Style.FILL
        overviewBitmap?.eraseColor(Color.TRANSPARENT)
        val canvas = overviewBitmap?.let { Canvas(it) }
        //繪製透明灰色背景
        canvas?.drawRect(0f, 0f, rectW, rectH, overviewPaint)

        overviewPaint.color = Color.WHITE
        for (i in 0 until row) {
            val top = i * rectHeight + i * overviewVerSpacing + overviewVerSpacing
            loop@ for (j in 0 until column) {

                val seatType = getSeatType(i, j)
                when (seatType) {
                    SEAT_TYPE_AVAILABLE -> overviewPaint.color = Color.WHITE
                    SEAT_TYPE_NOT_AVAILABLE -> continue@loop
                    SEAT_TYPE_SELECTED -> overviewPaint.color = overview_checked
                    SEAT_TYPE_SOLD -> overviewPaint.color = overview_sold
                }

                val left: Float

                left = j * rectWidth + j * overviewSpacing + overviewSpacing
                canvas?.drawRect(left, top, left + rectWidth, top + rectHeight, overviewPaint)
            }
        }

        return overviewBitmap
    }

    /**
     * 自動回彈
     * 整个大小不超过控件大小的时候:
     * 往左边滑动,自动回弹到行号右边
     * 往右边滑动,自动回弹到右边
     * 往上,下滑动,自动回弹到顶部
     *
     *
     * 整个大小超过控件大小的时候:
     * 往左侧滑动,回弹到最右边,往右侧滑回弹到最左边
     * 往上滑动,回弹到底部,往下滑动回弹到顶部
     */
    private fun autoScroll() {
        val currentSeatBitmapWidth = seatBitmapWidth * matrixScaleX
        val currentSeatBitmapHeight = seatBitmapHeight * matrixScaleY
        var moveYLength = 0f
        var moveXLength = 0f

        //處理左右滑動的情况
        if (currentSeatBitmapWidth < width) {
            if (translateX < 0 || matrixScaleX < numberWidth + spacing) {
                //計算要移動的距離

                if (translateX < 0) {
                    moveXLength = -translateX + numberWidth.toFloat() + spacing.toFloat()
                } else {
                    moveXLength = numberWidth + spacing - translateX
                }

            }
        } else {

            if (translateX < 0 && translateX + currentSeatBitmapWidth > width) {

            } else {
                //往左側滑動
                if (translateX + currentSeatBitmapWidth < width) {
                    moveXLength = width - (translateX + currentSeatBitmapWidth)
                } else {
                    //右側滑動
                    moveXLength = -translateX + numberWidth.toFloat() + spacing.toFloat()
                }
            }

        }

        val startYPosition =
            screenHeight * matrixScaleY + verSpacing * matrixScaleY + headHeight + borderHeight.toFloat()

        //處理上下滑動
        if (currentSeatBitmapHeight + headHeight < height) {

            if (translateY < startYPosition) {
                moveYLength = startYPosition - translateY
            } else {
                moveYLength = -(translateY - startYPosition)
            }

        } else {

            if (translateY < 0 && translateY + currentSeatBitmapHeight > height) {

            } else {
                //往上滑動
                if (translateY + currentSeatBitmapHeight < height) {
                    moveYLength = height - (translateY + currentSeatBitmapHeight)
                } else {
                    moveYLength = -(translateY - startYPosition)
                }
            }
        }

        val start = Point()
        start.x = translateX.toInt()
        start.y = translateY.toInt()

        val end = Point()
        end.x = (start.x + moveXLength).toInt()
        end.y = (start.y + moveYLength).toInt()

        moveAnimate(start, end)

    }

    fun autoScale() {

        if (matrixScaleX > 3.2) {
            zoomAnimate(matrixScaleX, 3.0f)
        } else if (matrixScaleX < 1.98) {
            zoomAnimate(matrixScaleX, 2.0f)
        }
    }

    private fun isHave(seat: Int?): Int {
        return Collections.binarySearch(selects, seat!!)
    }

    private fun remove(index: Int) {
        selects.removeAt(index)
    }

    private fun dip2Px(value: Float): Float {
        return resources.displayMetrics.density * value
    }

    private fun getBaseLine(p: Paint, top: Float, bottom: Float): Float {
        val fontMetrics = p.fontMetrics
        val baseline = ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2).toInt()
        return baseline.toFloat()
    }

    private fun moveAnimate(start: Point, end: Point) {
        val valueAnimator = ValueAnimator.ofObject(MoveEvaluator(), start, end)
        valueAnimator.interpolator = DecelerateInterpolator()
        val moveAnimation = MoveAnimation()
        valueAnimator.addUpdateListener(moveAnimation)
        valueAnimator.duration = 400
        valueAnimator.start()
    }

    private fun zoomAnimate(cur: Float, tar: Float) {
        val valueAnimator = ValueAnimator.ofFloat(cur, tar)
        valueAnimator.interpolator = DecelerateInterpolator()
        val zoomAnim = ZoomAnimation()
        valueAnimator.addUpdateListener(zoomAnim)
        valueAnimator.addListener(zoomAnim)
        valueAnimator.duration = 400 //400
        valueAnimator.start()
    }

    private fun zoom(zoom: Float) {
        val z = zoom / matrixScaleX
        matrix.postScale(z, z, scaleX, scaleY)
        invalidate()
    }

    private fun move(p: Point) {
        val x = p.x - translateX
        val y = p.y - translateY
        matrix.postTranslate(x, y)
        invalidate()
    }

    internal inner class MoveAnimation : ValueAnimator.AnimatorUpdateListener {

        override fun onAnimationUpdate(animation: ValueAnimator) {
            val p = animation.animatedValue as Point

            move(p)
        }
    }


    internal inner class MoveEvaluator : TypeEvaluator<Any> {

        override fun evaluate(fraction: Float, startValue: Any, endValue: Any): Any {
            val startPoint = startValue as Point
            val endPoint = endValue as Point
            val x = (startPoint.x + fraction * (endPoint.x - startPoint.x)).toInt()
            val y = (startPoint.y + fraction * (endPoint.y - startPoint.y)).toInt()
            return Point(x, y)
        }
    }

    internal inner class ZoomAnimation : ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

        override fun onAnimationUpdate(animation: ValueAnimator) {
            zoom = animation.animatedValue as Float
            zoom(zoom)

            if (DBG) {
                Log.d("zoomTest", "zoom:$zoom")
            }
        }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}

        override fun onAnimationStart(animation: Animator) {}

    }

    fun setData(row: Int, column: Int) {
        this.row = row
        this.column = column
        init()
        invalidate()
    }

    private fun addChooseSeat(row: Int, column: Int) {
        val id = getID(row, column)
        for (i in selects.indices) {
            val item = selects[i]
            if (id < item) {
                selects.add(i, id)
                return
            }
        }

        selects.add(id)
    }

    interface SeatChecker {
        /**
         * 是否可用座位
         *
         * @param row
         * @param column
         * @return
         */
        fun isValidSeat(row: Int, column: Int): Boolean

        /**
         * 是否已售
         *
         * @param row
         * @param column
         * @return
         */
        fun isSold(row: Int, column: Int): Boolean

        fun checked(row: Int, column: Int)

        fun unCheck(row: Int, column: Int)

        /**
         * 獲取選中後座位上顯示的文字
         * @param row
         * @param column
         * @return 返回2個元素的數組,第一個元素是第一行的文字,第二個元素是第二行文字,如果只返回一個元素則會繪製到座位圖的中間位置
         */
        fun checkedSeatTxt(row: Int, column: Int): Array<String>?

    }

    fun setScreenName(screenName: String) {
        this.screenName = screenName
    }

    fun setMaxSelected(maxSelected: Int) {
        this.maxSelected = maxSelected
    }

    fun setSeatChecker(seatChecker: SeatChecker) {
        this.seatChecker = seatChecker
        invalidate()
    }

    private fun getRowNumber(row: Int): Int {
        var result = row
        if (seatChecker == null) {
            return -1
        }

        for (i in 0 until row) {
            for (j in 0 until column) {
                if (seatChecker!!.isValidSeat(i, j)) {
                    break
                }

                if (j == column - 1) {
                    if (i == row) {
                        return -1
                    }
                    result--
                }
            }
        }
        return result
    }

    private fun getColumnNumber(row: Int, column: Int): Int {
        var result = column
        if (seatChecker == null) {
            return -1
        }

        for (i in row..row) {
            for (j in 0 until column) {

                if (!seatChecker!!.isValidSeat(i, j)) {
                    if (j == column) {
                        return -1
                    }
                    result--
                }
            }
        }
        return result
    }

    companion object {

        /**
         * 座位已售
         */
        private val SEAT_TYPE_SOLD = 1

        /**
         * 座位已經選中
         */
        private val SEAT_TYPE_SELECTED = 2

        /**
         * 座位可選
         */
        private val SEAT_TYPE_AVAILABLE = 3

        /**
         * 座位不可用
         */
        private val SEAT_TYPE_NOT_AVAILABLE = 4
    }

}