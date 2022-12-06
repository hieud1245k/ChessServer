package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.requests.ChessRequest

interface PlayerService {
    fun save(name: String): PlayerEntity
    fun removeById(playerId: Long)
    fun removeByName(name: String): Long
    fun gotoBoxOffline(chessRequest: ChessRequest): ChessRequest
}
