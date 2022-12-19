package com.hieuminh.chessserver.requests

import com.hieuminh.chessserver.entities.Box
import java.io.Serializable

class ChessRequest : Serializable {
    var from: Box? = null
    var to: Box? = null

    var playerName: String? = null

    var roomId: Long? = null

    var checkmate: Boolean = false

    var youWin: Boolean? = null

    fun reverse() {
        from?.y = 7 - (from?.y ?: 0)
        to?.y = 7 - (to?.y ?: 0)
    }
}
