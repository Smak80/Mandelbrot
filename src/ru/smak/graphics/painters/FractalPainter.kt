package ru.smak.graphics.painters

import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.convertation.Converter
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Graphics

class FractalPainter(var plane: CartesianScreenPlane,
                     val fractal: Mandelbrot
                     )
{

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
                if (fractal.isInSet(x, y)==0){
                    g.fillRect(i, j, 1, 1)
                }
            }
        }
    }

}