package com.hieuminh.chessserver.services.impl

import com.hieuminh.chessserver.boardGame.BoardSpec.Companion.toBoard
import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import com.hieuminh.chessserver.boardGame.players.MinimaxPlayer
import com.hieuminh.chessserver.entities.BoxEntity
import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.PlayerRepository
import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.services.PlayerService
import com.hieuminh.chessserver.services.RoomService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PlayerServiceImpl(
    private val playerRepository: PlayerRepository,
    private val roomService: RoomService,
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

    override fun gotoBoxOffline(chessRequest: ChessRequest): ChessRequest {
        val room = roomService.findById(chessRequest.roomId ?: 0)
        val minimaxPlayer = MinimaxPlayer(PieceColor.BlackSet)
        val board = (room.boardString ?: "").toBoard()
        board.play(chessRequest.from!!.toSquare(), chessRequest.to!!.toSquare())
        board.blackPlayer(minimaxPlayer)
        val boardValue = minimaxPlayer.move(board)
        val chessResponse = ChessRequest().apply {
            from = BoxEntity().apply {
                setPosition(boardValue.from.toPosition())
            }
            to = BoxEntity().apply {
                setPosition(boardValue.to.toPosition())
            }
        }
        board.play(boardValue)
        room.boardString = board.toPrettyString()
        roomService.save(room)
        return chessResponse
    }
}
