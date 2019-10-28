package ru.smak.math.fractals

import org.junit.jupiter.api.Assertions.*

internal class MandelbrotTest {

    @org.junit.jupiter.api.Test
    fun isInSet() {
        val m = Mandelbrot()
        assertEquals(true, m.isInSet(2.0, 2.0)>0)
        assertEquals(true, m.isInSet(-2.0, 2.0)>0)
        assertEquals(false, m.isInSet(0.0, 0.0)>0)
        assertEquals(false, m.isInSet(0.3, 0.3)>0)
    }
}