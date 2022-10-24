package com.hieuminh.chessserver.repositories

import com.hieuminh.chessserver.entities.PlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : JpaRepository<PlayerEntity, Long> {
    fun findByNameAndDeletedAtNull(name: String): PlayerEntity?
}
