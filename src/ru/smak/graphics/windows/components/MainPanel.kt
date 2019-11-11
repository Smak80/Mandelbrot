package ru.smak.graphics.windows.components

import ru.smak.graphics.painters.FractalPainter
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JPanel

class MainPanel (var painter: FractalPainter): JPanel(){
    init{
        addComponentListener(
            object: ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                repaint()
            }
        })
    }

    override fun paint(g: Graphics?) {
        painter.plane.realWidth = width
        painter.plane.realHeight = height
        super.paint(g)
        if (g!=null) painter.paint(g)
    }
}