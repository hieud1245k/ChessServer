package com.hieuminh.chessserver.entities

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {
    @Column(name = "player_first_id")
    var playerFirstId: Long? = null

    @Column(name = "player_second_id")
    var playerSecondId: Long? = null
}
