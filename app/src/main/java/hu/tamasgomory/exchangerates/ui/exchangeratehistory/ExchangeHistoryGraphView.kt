package hu.tamasgomory.exchangerates.ui.exchangeratehistory

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import hu.tamasgomory.exchangerates.R

/**
 * TODO: document your custom view class.
 */
class ExchangeHistoryGraphView : View {

    private var _graphColor: Int = Color.BLACK // TODO: use a default from R.color...
    private var _textColor: Int = Color.BLACK // TODO: use a default from R.color...
    private var _textSize: Float = 12f // TODO: use a default from R.dimen...

    private var textPaint: TextPaint? = null

    var headerModel: HeaderModel? = null
        set(value) {
            field = value
            if (!columns.isNullOrEmpty()) {
                invalidateTextPaintAndMeasurements()
            }
        }

    var columns: List<ColumnModel>? = null
        set(value) {
            field = value
            calculateColumnPositions()
            if (headerModel != null) {
                invalidateTextPaintAndMeasurements()
            }
        }

    var graphColor: Int
        get() = _graphColor
        set(value) {
            _graphColor = value
            invalidateTextPaintAndMeasurements()
        }

    var textColor: Int
        get() = _textColor
        set(value) {
            _textColor = value
            invalidateTextPaintAndMeasurements()
        }

    var textSize: Float
        get() = _textSize
        set(value) {
            _textSize = value
            invalidateTextPaintAndMeasurements()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.ExchangeHistoryGraphView, defStyle, 0)

        _graphColor = a.getColor(
                R.styleable.ExchangeHistoryGraphView_graphColor,
                graphColor)

        _textColor = a.getColor(
                R.styleable.ExchangeHistoryGraphView_textColor,
                textColor)

        _textSize = a.getDimension(
                R.styleable.ExchangeHistoryGraphView_textSize,
                textSize)

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    fun calculateColumnPositions() {
        columns?.let {cols ->
            if (cols.isNotEmpty()) {
                var xPos = 0f
                var xDelta = 1f / (cols.size - 1)

                var minRate = cols.minBy { it.rate }!!.rate.toFloat()
                var maxRate = cols.maxBy { it.rate }!!.rate.toFloat()
                var minMaxDiff = maxRate - minRate

                cols.forEach {
                    it.xPercent = xPos
                    xPos += xDelta

                    it.yPercent = 1-(it.rate.toFloat() - minRate) / minMaxDiff
                }
            }
        }
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint?.let {
            it.strokeWidth = 7f
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        canvas.apply {
            this@ExchangeHistoryGraphView.columns?.let { cols ->
                if (cols.isNotEmpty()) {
                    var previousXPos: Float? = null
                    var previousYPos: Float? = null

                    var labelHeight = 0f

                    textPaint?.let {
                        it.textSize = textSize
                        labelHeight = it.fontSpacing
                    }
                    val columnLabelYPos = contentHeight + paddingTop.toFloat()


                    cols.forEach {
                        val xPos = (contentWidth*it.xPercent) + paddingLeft
                        val yPos = ((contentHeight-labelHeight*3) * it.yPercent) + paddingTop + labelHeight

                        textPaint?.color = graphColor
                        if (previousXPos != null && previousYPos != null) {
                            drawLine(previousXPos!!, previousYPos!!, xPos, yPos, textPaint)
                        }
                        drawCircle(xPos, yPos, 10f, textPaint)

                        textPaint?.textAlign = Paint.Align.CENTER
                        textPaint?.color = textColor
                        drawText(it.rate.toString(), xPos, columnLabelYPos, textPaint)
                        drawText(it.date, xPos, columnLabelYPos+labelHeight, textPaint)

                        previousXPos = xPos
                        previousYPos = yPos

                    }
                }
            }

            textPaint?.color = textColor
            textPaint?.textAlign = Paint.Align.LEFT
            drawText(headerModel.toString(), paddingLeft.toFloat(), paddingTop.toFloat(), textPaint)
        }
    }

    class HeaderModel(val amount: Double, val baseCurrency: String, val targetCurrency: String) {
        override fun toString(): String {
            return "%s %s in %s".format(amount.toString(), baseCurrency, targetCurrency)
        }
    }
    data class ColumnModel(val rate: Double, val date: String) {
        var xPercent: Float = 0f
        var yPercent: Float = 0f
    }
}
