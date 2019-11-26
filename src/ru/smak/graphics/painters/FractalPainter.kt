package ru.smak.graphics.painters

import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.convertation.Converter
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import kotlin.concurrent.thread
import kotlin.math.abs

class FractalPainter(var plane: CartesianScreenPlane,
                     val fractal: Mandelbrot
                     )
{
    private var cs: (Float) -> Color
    private var buf: BufferedImage? = null
    private var notReady = -1

    init {
        cs = { if (abs(it) < 1e-10) Color.BLACK else Color.WHITE }

        plane.addResizeListener { old, new ->
            if (old != new && plane.realWidth > 0 && plane.realHeight > 0) {
                buf = BufferedImage(plane.realWidth, plane.realHeight, BufferedImage.TYPE_INT_RGB)
            }
        }

        if (plane.realWidth > 0 && plane.realHeight > 0)
            buf = BufferedImage(plane.realWidth, plane.realHeight, BufferedImage.TYPE_INT_RGB)

    }

    fun paint(gr: Graphics) {
        val g = buf?.graphics ?: gr
        g.clearRect(
            0,
            0,
            plane.realWidth,
            plane.realHeight
        )
        g.color = Color.BLACK
        /*for (i in 0..plane.width){
            for (j in 0..plane.height){
                val x =
                    Converter.xScr2Crt(i, plane)
                val y =
                    Converter.yScr2Crt(j, plane)
                g.color = cs(fractal.isInSet(x, y))
                g.fillRect(i, j, 1, 1)
            }
        }*/

        // Java-style
        //val p = P(g)
        //Thread(p).start()

        //Kotlin-style

        val threads: MutableList<Thread> = mutableListOf()
        val maxThreads = 4
        notReady = maxThreads
        for (k in 0 until maxThreads) {
            val kWidth = plane.width / maxThreads
            threads.add(k, thread {
                val min = k * kWidth
                val max = if (k == maxThreads - 1) plane.width else (k + 1) * kWidth - 1
                for (i in min..max) {
                    for (j in 0..plane.height) {
                        val x =
                            Converter.xScr2Crt(i, plane)
                        val y =
                            Converter.yScr2Crt(j, plane)
                        val d = fractal.isInSet(x, y)
                        synchronized(g) {
                            g.color = cs(d)
                            g.fillRect(i, j, 1, 1)
                        }
                    }
                }
                notReady--
            })
        }
        for (t in threads) {
            t.join()
        }
        gr.drawImage(buf, 0, 0, null)
    }

    fun setColorScheme(cs: (Float) -> Color) {
        this.cs = cs
    }

}

//Java-style
class P(var g: Graphics) : Runnable {
    override fun run() {
        g.color = Color.BLUE
        g.fillRect(10, 10, 300, 300)
    }
}