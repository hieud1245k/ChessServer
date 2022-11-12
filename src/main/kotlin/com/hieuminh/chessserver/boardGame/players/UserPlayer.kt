package com.hieuminh.chessserver.boardGame.players

import com.hieuminh.chessserver.boardGame.board.BoardView
import com.hieuminh.chessserver.boardGame.board.Move
import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import com.hieuminh.chessserver.boardGame.players.Player
import com.hieuminh.chessserver.boardGame.players.PlayerType
import java.io.Serializable

class UserPlayer(pieceColor: PieceColor) : Player(pieceColor, PlayerType.User), Serializable {

    override fun move(board: BoardView): Move {
        throw RuntimeException("$pieceColor requires user input.")
    }

    override fun toString() = pieceColor.toString() + "\t" + type
}
