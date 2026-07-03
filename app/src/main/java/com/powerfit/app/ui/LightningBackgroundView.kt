package com.powerfit.app.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class LightningBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rays = mutableListOf<Ray>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animationPhase = 0f
    private val maxRays = 12

    data class Ray(
        var startX: Float,
        var startY: Float,
        var angle: Float,
        var length: Float,
        var alpha: Float,
        var speed: Float,
        var width: Float,
        var color: Int,
        var segments: MutableList<PointF> = mutableListOf()
    )

    init {
        generateRays()
    }

    private fun generateRays() {
        rays.clear()
        val colors = intArrayOf(
            Color.parseColor("#FF2D55"),
            Color.parseColor("#7C4DFF"),
            Color.parseColor("#00E5FF"),
            Color.parseColor("#FF6B35")
        )

        for (i in 0 until maxRays) {
            val ray = Ray(
                startX = Random.nextFloat() * width,
                startY = Random.nextFloat() * height,
                angle = Random.nextFloat() * 360f,
                length = 200f + Random.nextFloat() * 400f,
                alpha = 0.1f + Random.nextFloat() * 0.3f,
                speed = 0.5f + Random.nextFloat() * 1.5f,
                width = 1f + Random.nextFloat() * 3f,
                color = colors[Random.nextInt(colors.size)]
            )
            generateRaySegments(ray)
            rays.add(ray)
        }
    }

    private fun generateRaySegments(ray: Ray) {
        ray.segments.clear()
        val segmentCount = 8 + Random.nextInt(6)
        val angleRad = Math.toRadians(ray.angle.toDouble()).toFloat()

        var x = ray.startX
        var y = ray.startY

        for (i in 0..segmentCount) {
            ray.segments.add(PointF(x, y))
            val segLength = ray.length / segmentCount
            val jitter = (Random.nextFloat() - 0.5f) * 60f
            x += cos(angleRad) * segLength + jitter
            y += sin(angleRad.toDouble()).toFloat() * segLength + jitter
        }
    }

    fun updateAnimation(deltaTime: Float) {
        animationPhase += deltaTime * 0.001f

        rays.forEachIndexed { index, ray ->
            ray.alpha = (0.1f + 0.2f * sin(animationPhase * ray.speed + index).toDouble().toFloat()).coerceIn(0.05f, 0.4f)

            if (Random.nextFloat() < 0.005f) {
                ray.startX = Random.nextFloat() * width
                ray.startY = Random.nextFloat() * height
                ray.angle = Random.nextFloat() * 360f
                ray.speed = 0.5f + Random.nextFloat() * 1.5f
                generateRaySegments(ray)
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.parseColor("#0A0A0F"))

        rays.forEach { ray ->
            if (ray.segments.size < 2) return@forEach

            paint.strokeWidth = ray.width
            paint.strokeCap = Paint.Cap.ROUND
            paint.style = Paint.Style.STROKE
            paint.color = ray.color
            paint.alpha = (ray.alpha * 255).toInt().coerceIn(0, 255)

            val path = Path()
            path.moveTo(ray.segments[0].x, ray.segments[0].y)

            for (i in 1 until ray.segments.size) {
                val prev = ray.segments[i - 1]
                val curr = ray.segments[i]
                val midX = (prev.x + curr.x) / 2f
                val midY = (prev.y + curr.y) / 2f
                path.quadTo(prev.x, prev.y, midX, midY)
            }

            paint.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
            canvas.drawPath(path, paint)

            paint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
            paint.alpha = (ray.alpha * 0.3f * 255).toInt().coerceIn(0, 255)
            canvas.drawPath(path, paint)

            paint.maskFilter = null
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (rays.isEmpty()) generateRays()
    }
}
