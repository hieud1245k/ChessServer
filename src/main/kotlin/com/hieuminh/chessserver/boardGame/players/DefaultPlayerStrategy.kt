package com.hieuminh.chessserver.boardGame.players

import com.hieuminh.chessserver.boardGame.board.BoardView
import com.hieuminh.chessserver.boardGame.board.Square
import com.hieuminh.chessserver.boardGame.board.allSquares
import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import com.hieuminh.chessserver.boardGame.pieces.PieceType
import java.io.Serializable

class DefaultPlayerStrategy : Serializable {
    operator fun invoke(board: BoardView, pieceColor: PieceColor) =
        allSquares.map { square: Square ->
            var acc = 0
            if (isPieceOurs(board, pieceColor, square)) {
                val piece = board.pieceAt(square)
                when (piece.pieceType) {
                    PieceType.Pawn -> acc += 100
                    PieceType.Knight -> acc += 300
                    PieceType.Bishop -> acc += 330
                    PieceType.Rook -> acc += 500
                    PieceType.Queen -> acc += 940
                    else -> {
                    }
                }
            } else if (isPieceTheirs(board, pieceColor, square)) {
                val piece = board.pieceAt(square)
                when (piece.pieceType) {
                    PieceType.Pawn -> acc -= 100
                    PieceType.Knight -> acc -= 300
                    PieceType.Bishop -> acc -= 330
                    PieceType.Rook -> acc -= 500
                    PieceType.King -> acc -= 940
                    else -> {
                    }
                }
            }
            acc
        }.sum()

    fun isPieceOurs(board: BoardView, pieceColor: PieceColor, square: Square) =
        board.pieceAt(square).color === pieceColor

    fun isPieceTheirs(board: BoardView, color: PieceColor, square: Square) =
        board.pieceAt(square).color !== color && !board.noneAt(square)
}
