package ru.smak.graphics.windows.components

import ru.smak.graphics.painters.FractalPainter
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class MainPanel (var painter: FractalPainter): JPanel(){
    init{

    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        if (g!=null) painter.paint(g)
    }
}