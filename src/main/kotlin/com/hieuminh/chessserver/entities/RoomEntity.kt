package com.hieuminh.chessserver.entities

import javax.persistence.*

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {

    @Column(name = "player_first_name")
    var playerFirstName: String? = null

    @Column(name = "player_second_name")
    var playerSecondName: String? = null
}
