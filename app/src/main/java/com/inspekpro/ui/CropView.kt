package com.inspekpro.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class CropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var imageBitmap: Bitmap? = null
    private val imageMatrix = Matrix()

    // Current transformations
    private var scale = 1f
    private var transX = 0f
    private var transY = 0f
    private var rotationDegrees = 0f

    // Crop box dimensions (1:1 center)
    val cropRect = RectF()
    private val cropPaint = Paint().apply {
        color = Color.parseColor("#99000000") // 60% dim overlay
        style = Paint.Style.FILL
    }
    private val borderPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }
    private val gridPaint = Paint().apply {
        color = Color.parseColor("#66FFFFFF") // Rule of thirds grid
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = true
    }
    private val cornerPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 6f
        isAntiAlias = true
    }

    // Touch Handling
    private var lastX = 0f
    private var lastY = 0f
    private var isDragging = false

    private val scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val focusX = detector.focusX
            val focusY = detector.focusY
            val scaleFactor = detector.scaleFactor

            val newScale = (scale * scaleFactor).coerceIn(0.5f, 10.0f)

            // Zoom relative to the focus point in screen coordinates
            val centerX = width / 2f
            val centerY = height / 2f
            val imgCenterX = centerX + transX
            val imgCenterY = centerY + transY

            val dx = focusX - imgCenterX
            val dy = focusY - imgCenterY

            val newImgCenterX = focusX - dx * (newScale / scale)
            val newImgCenterY = focusY - dy * (newScale / scale)

            transX = newImgCenterX - centerX
            transY = newImgCenterY - centerY

            scale = newScale
            updateMatrix()
            return true
        }
    })

    fun setImageBitmap(bitmap: Bitmap) {
        imageBitmap = bitmap
        scale = 1f
        transX = 0f
        transY = 0f
        rotationDegrees = 0f
        if (width > 0 && height > 0) {
            resetImageMatrix()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val size = minOf(w, h) * 0.85f // WhatsApp crop area is quite large, ~85% of smaller screen dimension
        val left = (w - size) / 2
        val top = (h - size) / 2
        cropRect.set(left, top, left + size, top + size)

        resetImageMatrix()
    }

    private fun resetImageMatrix() {
        val bitmap = imageBitmap ?: return
        val w = width.toFloat()
        val h = height.toFloat()
        if (w == 0f || h == 0f) return

        val bitmapW = bitmap.width.toFloat()
        val bitmapH = bitmap.height.toFloat()

        // Scale to cover the cropRect box completely (take max of scale factors)
        val scaleX = cropRect.width() / bitmapW
        val scaleY = cropRect.height() / bitmapH
        scale = maxOf(scaleX, scaleY)

        // Center the bitmap on screen initially (no offsets)
        transX = 0f
        transY = 0f
        rotationDegrees = 0f

        updateMatrix()
    }

    private fun updateMatrix() {
        val bitmap = imageBitmap ?: return
        imageMatrix.reset()

        val cx = bitmap.width / 2f
        val cy = bitmap.height / 2f

        // Apply transformations relative to bitmap center
        imageMatrix.postTranslate(-cx, -cy)
        imageMatrix.postScale(scale, scale)
        imageMatrix.postRotate(rotationDegrees)

        // Translate to the centered position + user offset
        imageMatrix.postTranslate(width / 2f + transX, height / 2f + transY)

        invalidate()
    }

    fun rotateImage() {
        rotationDegrees = (rotationDegrees + 90f) % 360f
        updateMatrix()
        snapImageToBounds()
    }

    private fun snapImageToBounds() {
        val bitmap = imageBitmap ?: return

        // Compute axis-aligned bounding box of transformed image
        val imgBounds = RectF()
        imageMatrix.mapRect(imgBounds, RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()))

        var newScale = scale
        val isRotated = (rotationDegrees / 90f).toInt() % 2 != 0
        val actualW = if (isRotated) bitmap.height else bitmap.width
        val actualH = if (isRotated) bitmap.width else bitmap.height

        val minScaleX = cropRect.width() / actualW
        val minScaleY = cropRect.height() / actualH
        val minScale = maxOf(minScaleX, minScaleY)

        if (scale < minScale) {
            newScale = minScale
        }

        if (newScale != scale) {
            scale = newScale
            updateMatrix()
            imageMatrix.mapRect(imgBounds, RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()))
        }

        var dx = 0f
        var dy = 0f

        if (imgBounds.left > cropRect.left) {
            dx = cropRect.left - imgBounds.left
        } else if (imgBounds.right < cropRect.right) {
            dx = cropRect.right - imgBounds.right
        }

        if (imgBounds.top > cropRect.top) {
            dy = cropRect.top - imgBounds.top
        } else if (imgBounds.bottom < cropRect.bottom) {
            dy = cropRect.bottom - imgBounds.bottom
        }

        if (dx != 0f || dy != 0f) {
            transX += dx
            transY += dy
            updateMatrix()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        if (scaleDetector.isInProgress) {
            isDragging = false
            return true
        }

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                isDragging = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dx = event.x - lastX
                    val dy = event.y - lastY
                    transX += dx
                    transY += dy
                    lastX = event.x
                    lastY = event.y
                    updateMatrix()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                snapImageToBounds()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmap = imageBitmap ?: return

        // 1. Draw original bitmap with transformations
        canvas.save()
        canvas.concat(imageMatrix)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        canvas.restore()

        // 2. Draw dim overlay (WhatsApp overlay is darker and circular or rectangular)
        // Note: The crop bounds is 1:1 rectangular but wait, does it have circular or rectangular clip?
        // Requirements say "Crop area berbentuk 1 : 1. Overlay gelap di luar area crop."
        // We clip out the cropRect.
        canvas.save()
        canvas.clipOutRect(cropRect)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), cropPaint)
        canvas.restore()

        // 3. Draw crop box border
        canvas.drawRect(cropRect, borderPaint)

        // 4. Draw rule of thirds grid (Rule of Thirds)
        val stepX = cropRect.width() / 3
        val stepY = cropRect.height() / 3
        canvas.drawLine(cropRect.left + stepX, cropRect.top, cropRect.left + stepX, cropRect.bottom, gridPaint)
        canvas.drawLine(cropRect.left + 2 * stepX, cropRect.top, cropRect.left + 2 * stepX, cropRect.bottom, gridPaint)
        canvas.drawLine(cropRect.left, cropRect.top + stepY, cropRect.right, cropRect.top + stepY, gridPaint)
        canvas.drawLine(cropRect.left, cropRect.top + 2 * stepY, cropRect.right, cropRect.top + 2 * stepY, gridPaint)

        // 5. Draw WhatsApp style thick corners (drawn slightly outward of cropRect)
        val cornerLen = 40f
        // Top Left
        canvas.drawLine(cropRect.left - 3, cropRect.top - 3, cropRect.left + cornerLen, cropRect.top - 3, cornerPaint)
        canvas.drawLine(cropRect.left - 3, cropRect.top - 3, cropRect.left - 3, cropRect.top + cornerLen, cornerPaint)
        // Top Right
        canvas.drawLine(cropRect.right + 3, cropRect.top - 3, cropRect.right - cornerLen, cropRect.top - 3, cornerPaint)
        canvas.drawLine(cropRect.right + 3, cropRect.top - 3, cropRect.right + 3, cropRect.top + cornerLen, cornerPaint)
        // Bottom Left
        canvas.drawLine(cropRect.left - 3, cropRect.bottom + 3, cropRect.left + cornerLen, cropRect.bottom + 3, cornerPaint)
        canvas.drawLine(cropRect.left - 3, cropRect.bottom + 3, cropRect.left - 3, cropRect.bottom - cornerLen, cornerPaint)
        // Bottom Right
        canvas.drawLine(cropRect.right + 3, cropRect.bottom + 3, cropRect.right - cornerLen, cropRect.bottom + 3, cornerPaint)
        canvas.drawLine(cropRect.right + 3, cropRect.bottom + 3, cropRect.right + 3, cropRect.bottom - cornerLen, cornerPaint)
    }

    fun getCroppedBitmap(): Bitmap? {
        val bitmap = imageBitmap ?: return null

        val targetSize = 480 // Target size is 480x480 for sharp profile photos
        val cropped = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(cropped)

        val scaleFactor = targetSize / cropRect.width()
        val cropMatrix = Matrix(imageMatrix)
        cropMatrix.postTranslate(-cropRect.left, -cropRect.top)
        cropMatrix.postScale(scaleFactor, scaleFactor)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(bitmap, cropMatrix, paint)

        return cropped
    }
}
