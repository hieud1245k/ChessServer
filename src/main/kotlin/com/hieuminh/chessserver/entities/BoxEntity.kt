package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.enums.ChessmanType
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "boxes")
class BoxEntity : BaseEntity() {
    var x: Int = 0
    var y: Int = 0

    @Column(name = "chessman_type")
    var chessmanType: ChessmanType? = null

    @Column(name = "room_id")
    var roomId: Long = 0
}
