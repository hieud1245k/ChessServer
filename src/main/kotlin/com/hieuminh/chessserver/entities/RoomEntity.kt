package com.hieuminh.chessserver.entities

import com.hieuminh.chessserver.boardGame.Yagoc
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "rooms")
class RoomEntity : BaseEntity() {

    @Column(name = "player_first_name")
    var playerFirstName: String? = null

    @Column(name = "player_second_name")
    var playerSecondName: String? = null

    @Column(name = "first_play")
    var firstPlay: String? = null

    @Column(name = "is_online")
    var isOnline: Boolean = true

    @Column(name = "board_string")
    var boardString: String? = Yagoc.boardString

    fun isFullPlayer(): Boolean {
        return playerFirstName != null && playerSecondName != null
    }

    fun removeRivalPlayerName(rivalName: String): Boolean {
        if (playerFirstName == rivalName) {
            playerFirstName = null
            return true
        }
        if (playerSecondName == rivalName) {
            playerSecondName = null
            return true
        }
        return false
    }
}
