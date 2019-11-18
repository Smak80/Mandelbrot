package ru.smak.graphics.painters

import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.convertation.Converter
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Graphics
import kotlin.concurrent.thread
import kotlin.math.abs

class FractalPainter(var plane: CartesianScreenPlane,
                     val fractal: Mandelbrot
                     )
{
    private var cs: (Float) -> Color

    init {
        cs = { if (abs(it) < 1e-10) Color.BLACK else Color.WHITE }
    }

    fun paint(g: Graphics){
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
        for (k in 0 until 4) {
            val kWidth = plane.width / 4
            threads.add(thread {
                val min = k * kWidth
                val max = if (k == 3) plane.width else (k + 1) * kWidth - 1
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
            })
        }
        for (t in threads) {
            t.join()
        }
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