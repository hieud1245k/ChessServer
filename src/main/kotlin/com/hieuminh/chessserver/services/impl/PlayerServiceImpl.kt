package com.hieuminh.chessserver.services.impl

import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.PlayerRepository
import com.hieuminh.chessserver.services.PlayerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PlayerServiceImpl(
    private val playerRepository: PlayerRepository,
) : PlayerService {
    override fun save(name: String): PlayerEntity {
        val playerEntity = playerRepository.findByNameAndDeletedAtNull(name)
        if (playerEntity != null) {
            throw CustomException("Username \"${name}\" is exist!", HttpStatus.CONFLICT)
        }
        return playerRepository.save(PlayerEntity().apply {
            this.name = name
        })
    }

    override fun removeByName(name: String) {
        val playerEntity = playerRepository.findByNameAndDeletedAtNull(name)
            ?: throw CustomException("", HttpStatus.NOT_FOUND)
        playerEntity.deletedAt = LocalDate.now()
        playerRepository.save(playerEntity)
    }
}
