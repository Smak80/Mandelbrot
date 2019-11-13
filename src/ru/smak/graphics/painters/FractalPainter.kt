package ru.smak.graphics.painters

import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.convertation.Converter
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Graphics
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
        for (i in 0..plane.width){
            for (j in 0..plane.height){
                val x =
                    Converter.xScr2Crt(i, plane)
                val y =
                    Converter.yScr2Crt(j, plane)
                g.color = cs(fractal.isInSet(x, y))
                g.fillRect(i, j, 1, 1)
            }
        }
    }

    fun setColorScheme(cs: (Float) -> Color) {
        this.cs = cs
    }

}