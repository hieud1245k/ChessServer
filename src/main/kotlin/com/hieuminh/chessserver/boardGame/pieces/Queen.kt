package com.hieuminh.chessserver.boardGame.pieces

import com.hieuminh.chessserver.boardGame.board.BoardView
import com.hieuminh.chessserver.boardGame.board.Move
import com.hieuminh.chessserver.boardGame.board.Square
import java.util.stream.Stream

class Queen(pieceColor: PieceColor) : Piece(PieceType.Queen, pieceColor) {
    public override fun isValidForPiece(board: BoardView, move: Move) =
        Bishop.isCorrectMoveForBishop(board, move) || Rook.isCorrectMoveForRook(board, move)

    public override fun generateMovesForPiece(board: BoardView, from: Square) = Stream.concat(
        Bishop.generateMovesForBishop(board, from),
        Rook.generateMovesForRook(board, from)
    )
}
