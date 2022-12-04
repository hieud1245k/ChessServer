package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.requests.ChessRequest

interface RoomService {
    fun getAll(): List<RoomEntity>
    fun createNew(name: String): RoomEntity
    fun findById(id: Long): RoomEntity
    fun joinRoom(id: Long, name: String): RoomEntity
    fun removeByPlayerName(name: String)
    fun leaveRoom(roomEntity: RoomEntity): RoomEntity
    fun startOfflineGame(name: String): RoomEntity
    fun save(roomEntity: RoomEntity): RoomEntity
    fun playNow(name: String): RoomEntity
    fun goToBox(message: String): ChessRequest
    fun getMoveSuggestions(chessRequest: ChessRequest) : List<ChessRequest>
}
