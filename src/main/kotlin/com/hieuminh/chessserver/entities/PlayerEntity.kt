package com.hieuminh.chessserver.entities

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "players")
class PlayerEntity : BaseEntity() {
    @Column(name = "name")
    var username: String = ""
}
