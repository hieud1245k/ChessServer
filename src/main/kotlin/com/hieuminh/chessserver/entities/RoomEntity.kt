package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.boardGame.Yagoc
import javax.persistence.Entity

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {
    var playerFirstId: Long? = null

    var playerSecondId: Long? = null

    var firstPlay: Long? = null

    var isOnline: Boolean = true

    var boardString: String? = Yagoc.boardString
}
