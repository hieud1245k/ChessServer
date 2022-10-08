package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.PlayerEntity

interface PlayerService {
    fun save(player: PlayerEntity) : PlayerEntity
}
