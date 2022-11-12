package com.hieuminh.chessserver.boardGame.players

import com.hieuminh.chessserver.boardGame.board.BoardView
import com.hieuminh.chessserver.boardGame.board.Move
import com.hieuminh.chessserver.boardGame.pieces.PieceColor

abstract class Player(val pieceColor: PieceColor, val type: PlayerType) {
    abstract fun move(board: BoardView): Move
    val isUser = type == PlayerType.User
    val isComputer = type == PlayerType.Computer
}
