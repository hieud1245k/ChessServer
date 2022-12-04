package com.hieuminh.chessserver.requests

import com.hieuminh.chessserver.entities.BoxEntity
import com.hieuminh.chessserver.entities.RoomEntity
import java.io.Serializable

class ChessRequest : Serializable {
    var from: BoxEntity? = null
    var to: BoxEntity? = null

    var playerName: String? = null

    var rivalPlayerName: String? = null

    var roomId: Long? = null

    var level: Int = 1

    var checkmate: Boolean = false

    var roomEntity: RoomEntity? = null

    val isMoveSuggestionsOn: Boolean
        get() = roomEntity?.getIsMoveSuggestionsOn(rivalPlayerName ?: "") ?: false

    fun reverse() {
        val playerName = playerName
        this.playerName = rivalPlayerName
        rivalPlayerName = playerName

        reversePosition()
    }

    fun reversePosition() {
        from?.y = 7 - (from?.y ?: 0)
        to?.y = 7 - (to?.y ?: 0)
    }

    override fun toString(): String {
        return "$from -> $to"
    }
}
