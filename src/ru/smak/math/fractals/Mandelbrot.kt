package ru.smak.math.fractals

import ru.smak.graphics.convertation.CartesianPlane
import ru.smak.math.Complex

class Mandelbrot() {
    var maxIter = 200
    val R = 2.0
    fun isInSet(x: Double, y: Double): Int{
        val c = Complex(x, y)
        var z = Complex()
        val R2 = R*R
        for (i in 1..maxIter){
            z = z * z + c
            if (z.arg2 > R2){
                return i
            }
        }
        return 0
    }
}