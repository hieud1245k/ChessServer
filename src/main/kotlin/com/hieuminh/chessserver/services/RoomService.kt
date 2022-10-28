package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.RoomEntity

interface RoomService {
    fun getAll(): List<RoomEntity>
    fun createNew(name: String): RoomEntity
    fun findById(id: Long): RoomEntity
    fun joinRoom(id: Long, name: String): RoomEntity
    fun removeByPlayerName(name : String)
}
