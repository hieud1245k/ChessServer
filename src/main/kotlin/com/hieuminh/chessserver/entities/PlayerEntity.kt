package com.hieuminh.chessserver.entities

import javax.persistence.Entity

@Entity(name = "players")
class PlayerEntity : BaseEntity() {
    var name: String = ""

    var isActive: Boolean = false
}
