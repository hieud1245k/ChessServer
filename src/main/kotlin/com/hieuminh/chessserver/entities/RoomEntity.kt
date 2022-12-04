package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.boardGame.Yagoc
import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import javax.persistence.Entity

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {
    var playerFirstName: String? = null

    var playerSecondName: String? = null

    var isPlayerFirstMoveSuggestionsOn: Boolean = false

    var isPlayerSecondMoveSuggestionsOn: Boolean = false

    var firstLevel: Int = 1

    var secondLevel: Int = 1

    var firstPlay: String? = null

    var isOnline: Boolean = true

    var boardString: String? = Yagoc.boardString

    fun getRivalPlayerName(name: String?): String? {
        if (name.equals(playerFirstName)) {
            return playerSecondName
        }
        return playerFirstName
    }

    fun setIsMoveSuggestionsOn(playerName: String, isMoveSuggestionOn: Boolean, level: Int) {
        when (playerName) {
            playerFirstName -> {
                isPlayerFirstMoveSuggestionsOn = isMoveSuggestionOn
                firstLevel = level
            }
            playerSecondName -> {
                isPlayerSecondMoveSuggestionsOn = isMoveSuggestionOn
                secondLevel = level
            }
        }
    }

    fun getIsMoveSuggestionsOn(playerName: String): Boolean {
        return when (playerName) {
            playerFirstName -> isPlayerFirstMoveSuggestionsOn
            playerSecondName -> isPlayerSecondMoveSuggestionsOn
            else -> false
        }
    }

    fun getPieceColor(playerName: String?): PieceColor {
        return if (playerName == playerFirstName) {
            PieceColor.WhiteSet
        } else {
            PieceColor.BlackSet
        }
    }
}
