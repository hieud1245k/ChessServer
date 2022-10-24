package com.hieuminh.chessserver.requests

import com.hieuminh.chessserver.entities.BoxEntity
import java.io.Serializable

class ChessRequest : Serializable {
    var from: BoxEntity? = null
    var to: BoxEntity? = null

    var playerName: String? = null
    var roomId: Long? = null

    fun reverse() {
        from?.y = 7 - (from?.y ?: 0)
        to?.y = 7 - (to?.y ?: 0)
    }
}
