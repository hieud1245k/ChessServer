package com.hieuminh.chessserver.entities

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {
    @Column(name = "player_first_session_id")
    var playerFirstSessionId: String? = null

    @Column(name = "player_second_section_id")
    var playerSecondSessionId: String? = null
}
