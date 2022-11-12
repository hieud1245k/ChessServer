package com.hieuminh.chessserver.boardGame

import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import com.hieuminh.chessserver.boardGame.players.MinimaxPlayer
import com.hieuminh.chessserver.boardGame.players.Player
import com.hieuminh.chessserver.boardGame.players.UserPlayer

data class YagocSettings(val blackPlayer: Player, val whitePlayer: Player)

const val defaultLevel = 4

val defaultSettings = YagocSettings(
    MinimaxPlayer(PieceColor.BlackSet, defaultLevel),
    UserPlayer(PieceColor.WhiteSet)
)
