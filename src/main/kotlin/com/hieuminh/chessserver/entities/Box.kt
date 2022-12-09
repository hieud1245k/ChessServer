package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.boardGame.board.Square
import com.hieuminh.chessserver.enums.ChessmanType

class Box{
    var x: Int = 0
    var y: Int = 0
    var chessmanType: ChessmanType? = null
    var roomId: Long = 0

    fun setPosition(pair: Pair<Int, Int>) {
        x = pair.first
        y = pair.second
    }

    fun toSquare(): Square {
        return Square((7 - y) * 8 + x)
    }
}
