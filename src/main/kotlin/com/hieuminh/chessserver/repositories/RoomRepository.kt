package com.hieuminh.chessserver.repositories

import com.hieuminh.chessserver.entities.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : JpaRepository<RoomEntity, Long> {
}
