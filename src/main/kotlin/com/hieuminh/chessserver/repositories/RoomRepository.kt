package com.hieuminh.chessserver.repositories

import com.hieuminh.chessserver.entities.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findAllByDeletedAtNull(): List<RoomEntity>
    fun findAllByDeletedAtNullAndIsOnlineTrue(): List<RoomEntity>
    fun findAllByPlayerFirstNameOrPlayerSecondName(firstName: String, secondName: String): List<RoomEntity>
    fun findByIdAndDeletedAtNull(id: Long): Optional<RoomEntity>
    fun findTop1ByDeletedAtNullAndIsOnlineTrueAndPlayerFirstNameNullOrPlayerSecondNameNull(): Optional<RoomEntity>
}
