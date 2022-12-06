package com.hieuminh.chessserver.repositories

import com.hieuminh.chessserver.entities.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findAllByDeletedAtNull(): List<RoomEntity>
    fun findAllByDeletedAtNullAndIsOnlineTrue(): List<RoomEntity>
    fun findAllByPlayerFirstIdOrPlayerSecondId(playerFirstId: Long, playerSecondId: Long): List<RoomEntity>
    fun findByIdAndDeletedAtNull(roomId: Long): Optional<RoomEntity>
    fun findTop1ByDeletedAtNullAndIsOnlineTrueAndPlayerFirstIdNullOrPlayerSecondIdNull(): Optional<RoomEntity>
}
