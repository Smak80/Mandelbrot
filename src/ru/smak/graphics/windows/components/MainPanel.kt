package ru.smak.graphics.windows.components

import ru.smak.graphics.convertation.Converter
import ru.smak.graphics.painters.FractalPainter
import ru.smak.graphics.painters.SelectionRectPainter
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JPanel
import javax.swing.SwingWorker

class MainPanel (var painter: FractalPainter): JPanel(){
    val srp = SelectionRectPainter()

    inner class BackgroundProcess : SwingWorker<Unit, Unit>() {
        override fun doInBackground() {
            painter.create()
        }

        override fun done() {
            painter.paint(this@MainPanel.graphics)
        }
    }

    private var bgProcess = BackgroundProcess()

    init{
        addComponentListener(
            object: ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                painter.created = false
                repaint()
            }
        })
        addMouseListener(object : MouseAdapter() {
            override fun mouseReleased(e: MouseEvent?) {
                super.mouseReleased(e)
                if (e != null && e.button == MouseEvent.BUTTON1) {
                    srp.stop()
                    val x1 = Converter.xScr2Crt(srp.leftTopPoint.x, painter.plane)
                    val y1 = Converter.yScr2Crt(srp.leftTopPoint.y, painter.plane)
                    val x2 = Converter.xScr2Crt(srp.rightBottomPoint.x, painter.plane)
                    val y2 = Converter.yScr2Crt(srp.rightBottomPoint.y, painter.plane)
                    painter.plane.xMin = x1
                    painter.plane.xMax = x2
                    painter.plane.yMax = y1
                    painter.plane.yMin = y2
                    painter.created = false
                    repaint()
                }
            }

            override fun mousePressed(e: MouseEvent?) {
                super.mousePressed(e)
                if (e != null && e.button == MouseEvent.BUTTON1) {
                    srp.start(e.point)
                    srp.g = graphics
                }
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                super.mouseDragged(e)
                if (e != null) {
                    srp.shift(e.point)
                }
            }
        })
    }

    override fun paint(g: Graphics?) {
        painter.plane.realWidth = width
        painter.plane.realHeight = height
        super.paint(g)
        g?.let { painter.paint(it) }
        if (!painter.created) {
            if (!bgProcess.isDone) bgProcess.cancel(true)
            bgProcess = BackgroundProcess()
            bgProcess.execute()
        }
    }
}