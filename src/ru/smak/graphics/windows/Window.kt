package ru.smak.graphics.windows

import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.painters.FractalPainter
import ru.smak.graphics.windows.components.MainPanel
import ru.smak.math.fractals.Mandelbrot
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

class Window : JFrame(){
    private val mainPanel: MainPanel
    private val controlPanel: JPanel
    private val btnExit: JButton
    private val cbColor: JCheckBox
    private val cbProp: JCheckBox

    private val dim: Dimension

    private val painter: FractalPainter

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        dim = Dimension(500, 500)
        minimumSize = dim

        val plane = CartesianScreenPlane(
            -1,
            -1,
            -2.0,
            1.0,
            -1.0,
            1.0
        )

        val m = Mandelbrot()
        painter = FractalPainter(plane, m)

        mainPanel = MainPanel(painter)
        //mainPanel.background = Color.WHITE
        controlPanel = JPanel()
        controlPanel.border =
            BorderFactory.createTitledBorder(
                "Управление отображением"
            )
        btnExit = JButton("Выход")
        cbColor = JCheckBox("Цвет", false)
        cbProp = JCheckBox("Соблюдение пропорций",
                    false)
        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel,
                    (dim.height*0.7).toInt(),
                    GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        )
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(mainPanel,
                            (dim.width*0.9).toInt(),
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE
                        )
                        .addComponent(controlPanel)
                )
                .addGap(4)
        )

        val gl2 = GroupLayout(controlPanel)
        controlPanel.layout = gl2

        gl2.setVerticalGroup(
            gl2.createSequentialGroup()
                .addGap(4)
                .addGroup(
                    gl2.createParallelGroup(
                        GroupLayout.Alignment.CENTER
                    )
                        .addGroup(
                            gl2.createSequentialGroup()
                                .addComponent(cbColor,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                                .addGap(4)
                                .addComponent(cbProp,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                        )
                        .addComponent(btnExit,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE)
                )
                .addGap(4)
        )
        gl2.setHorizontalGroup(
            gl2.createSequentialGroup()
                .addGap(4)
                .addGroup(
                    gl2.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                    )
                        .addComponent(cbColor)
                        .addComponent(cbProp)

                )
                .addGap(4, 4, Int.MAX_VALUE)
                .addComponent(btnExit)
                .addGap(4)
        )

        pack()
        painter.plane.realWidth = mainPanel.width
        painter.plane.realHeight = mainPanel.height
        isVisible = true
    }
}