package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.requests.base.BaseRequest

interface PlayerService {
    fun save(name: String): PlayerEntity
    fun removeByName(name : String)
    fun gotoBoxOffline(chessRequest: ChessRequest): ChessRequest
}
