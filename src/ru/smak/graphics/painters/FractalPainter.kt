package ru.smak.graphics.painters

import ru.smak.graphics.convertation.CartesianScreenPlane
import java.awt.Color
import java.awt.Graphics

class FractalPainter(var plane: CartesianScreenPlane) {

    fun paint(g: Graphics){
        g.clearRect(0, 0, plane.realWidth, plane.realHeight)
        g.color = Color.BLUE
        g.fillOval(30, 30, 100, 100)
    }

}