package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.RoomEntity

interface RoomService {
    fun getAll(): List<RoomEntity>
    fun createNew(playerId: Long): RoomEntity
    fun findById(roomId: Long): RoomEntity
    fun joinRoom(roomId: Long, playerId: Long): RoomEntity
    fun removeByPlayerId(playerId: Long)
    fun leaveRoom(roomEntity: RoomEntity): RoomEntity
    fun startOfflineGame(playerId: Long): RoomEntity
    fun save(roomEntity: RoomEntity): RoomEntity
    fun playNow(playerId: Long): RoomEntity
    fun goToBox(message: String): Pair<Long, String>
}
