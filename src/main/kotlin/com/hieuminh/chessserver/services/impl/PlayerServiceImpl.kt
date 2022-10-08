package com.hieuminh.chessserver.services.impl

import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.PlayerRepository
import com.hieuminh.chessserver.services.PlayerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PlayerServiceImpl(
    private val playerRepository: PlayerRepository,
) : PlayerService {

    override fun save(player: PlayerEntity): PlayerEntity {
        if (playerRepository.findByName(player.name) != null) {
            throw CustomException("Username \"${player.name}\" is exist!", HttpStatus.CONFLICT)
        }
        return playerRepository.save(player)
    }
}
