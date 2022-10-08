package com.hieuminh.chessserver.services

import com.hieuminh.chessserver.entities.RoomEntity

interface RoomService {
    fun getAll(): List<RoomEntity>
    fun createNew() : RoomEntity
}
