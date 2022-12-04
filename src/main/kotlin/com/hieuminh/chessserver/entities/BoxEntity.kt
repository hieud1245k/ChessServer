package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.boardGame.board.Square
import com.hieuminh.chessserver.enums.ChessmanType
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "boxes")
class BoxEntity : BaseEntity() {
    var x: Int = 0
    var y: Int = 0

    @Column(name = "chessman_type")
    var chessmanType: ChessmanType? = null

    @Column(name = "room_id")
    var roomId: Long = 0

    fun setPosition(pair: Pair<Int, Int>) {
        x = pair.first
        y = pair.second
    }

    fun toSquare(isFirstPlayer: Boolean = true): Square {
        val y = if (isFirstPlayer) 7 - y else y
        return Square(y * 8 + x)
    }

    override fun toString(): String {
        return "($x,$y)"
    }
}
