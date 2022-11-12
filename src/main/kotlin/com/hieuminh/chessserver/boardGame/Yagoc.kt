package com.hieuminh.chessserver.boardGame

import java.awt.Font
import javax.swing.JTextPane

object Yagoc {
    private val textPane = JTextPane().also {
        it.isEditable = false
        it.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
    }

    val logger = Logger(textPane)

//    @JvmStatic
//    fun main(args: Array<String>) {
//        System.setProperty("apple.laf.useScreenMenuBar", "true")
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
//        val board = Board(defaultSettings)
//        val yagocWindow = YagocWindow(Controller(board, SwingUIAdapter()), textPane)
//        yagocWindow.title = "Yet Another Game Of Chess"
//        yagocWindow.isVisible = true
//    }

    const val boardString = """
            RNBQKBNR
            PPPPPPPP
            --------
            --------
            --------
            --------
            pppppppp
            rnbqkbnr
        """
}
