package com.hieuminh.chessserver.services.impl

import com.google.gson.Gson
import com.hieuminh.chessserver.boardGame.BoardSpec.Companion.toBoard
import com.hieuminh.chessserver.entities.PlayerEntity
import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.PlayerRepository
import com.hieuminh.chessserver.repositories.RoomRepository
import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.services.RoomService
import com.hieuminh.chessserver.utils.JsonUtils
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RoomServiceImpl(
    private val roomRepository: RoomRepository,
    private val playerRepository: PlayerRepository,
    private val messagingTemplate: SimpMessageSendingOperations,
) : RoomService {
    private val random = java.util.Random()

    override fun getAll(): List<RoomEntity> {
        return roomRepository.findAllByDeletedAtNullAndIsOnlineTrue()
    }

    override fun createNew(name: String): RoomEntity {
        val roomEntity = RoomEntity()
        roomEntity.playerFirstName = name
        return roomRepository.save(roomEntity)
    }

    override fun findById(id: Long): RoomEntity {
        val room = roomRepository.findByIdAndDeletedAtNull(id)
        if (room.isEmpty) {
            throw CustomException(HttpStatus.NOT_FOUND, "Room with id $id is not found!")
        }
        return room.get()
    }

    override fun joinRoom(id: Long, name: String): RoomEntity {
        val room = findById(id)
        if (room.playerFirstName != null && room.playerSecondName != null) {
            throw CustomException(HttpStatus.CONFLICT, "Room with id $id is full player!")
        }
        if (room.playerFirstName != null) {
            room.playerSecondName = name
        } else {
            room.playerFirstName = name
        }
        return roomRepository.save(room)
    }

    override fun removeByPlayerName(name: String) {
        val playerEntities = roomRepository.findAllByPlayerFirstNameOrPlayerSecondName(name, name)
        playerEntities.forEach { roomEntity ->
            when {
                name == roomEntity.playerFirstName && roomEntity.playerSecondName != null -> {
                    roomEntity.playerFirstName = null
                }
                name == roomEntity.playerSecondName && roomEntity.playerFirstName != null -> {
                    roomEntity.playerSecondName = null
                }
                else -> {
                    roomEntity.deletedAt = LocalDate.now()
                }
            }
            roomRepository.save(roomEntity)
            messagingTemplate.convertAndSend("/queue/join-room/${roomEntity.id}", Gson().toJson(roomEntity))
        }
    }

    override fun leaveRoom(roomEntity: RoomEntity): RoomEntity {
        if (roomEntity.id == 0L || roomEntity.playerFirstName != null && roomEntity.playerSecondName != null) {
            throw CustomException(HttpStatus.BAD_REQUEST, "Data is invalid!")
        }
        if (roomEntity.playerFirstName == null && roomEntity.playerSecondName == null || !roomEntity.isOnline) {
            roomEntity.deletedAt = LocalDate.now()
        }
        return roomRepository.save(roomEntity)
    }

    override fun startOfflineGame(playerEntity: PlayerEntity): RoomEntity {
        val player = playerRepository.findByNameAndDeletedAtNull(playerEntity.name)
            ?: throw CustomException(HttpStatus.BAD_REQUEST)
        player.level = playerEntity.level
        playerRepository.save(player)
        val room = RoomEntity()
        room.playerFirstName = player.name
        room.isOnline = false
        room.firstPlay = player.name
        return roomRepository.save(room)
    }

    override fun save(roomEntity: RoomEntity): RoomEntity {
        return roomRepository.save(roomEntity)
    }

    override fun playNow(name: String): RoomEntity {
        val optional =
            roomRepository.findTop1ByDeletedAtNullAndIsOnlineTrueAndPlayerFirstNameNullOrPlayerSecondNameNull()
        if (optional.isEmpty) {
            throw CustomException(HttpStatus.BAD_REQUEST, "Bad request")
        }
        val roomEntity = optional.get()
        when {
            roomEntity.playerFirstName == null && roomEntity.playerSecondName == null -> {
                throw CustomException(HttpStatus.BAD_REQUEST, "Bad request")
            }
            roomEntity.playerFirstName == null -> {
                roomEntity.playerFirstName = name
            }
            roomEntity.playerSecondName == null -> {
                roomEntity.playerSecondName = name
            }
        }
        return roomRepository.save(roomEntity)
    }

    override fun goToBox(message: String): Pair<Long, String> {
        val chessRequest =
            JsonUtils.fromJson<ChessRequest>(message) ?: throw CustomException(HttpStatus.BAD_REQUEST, "Bad request")
        val room = findById(chessRequest.roomId ?: 0)
        val board = room.boardString?.toBoard()
        val firstPlayer = chessRequest.playerName == room.playerSecondName // PlayerName is the name of rival player
        try {
            board?.play(chessRequest.from!!.toSquare(firstPlayer), chessRequest.to!!.toSquare(firstPlayer))
        } catch (e: Exception) {
            e.printStackTrace()
            chessRequest.isInValidMove = true
            return Pair(room.id, JsonUtils.toJson(chessRequest))
        }
        room.boardString = board?.toPrettyString()
        roomRepository.save(room)
        chessRequest.reverse()
        return Pair(room.id, JsonUtils.toJson(chessRequest))
    }

    override fun kickTheOpponent(roomEntity: RoomEntity, rivalName: String): RoomEntity {
        if (!roomEntity.isFullPlayer() || !roomEntity.removeRivalPlayerName(rivalName)) {
            throw CustomException(HttpStatus.BAD_REQUEST, "")
        }
        return roomRepository.save(roomEntity)
    }
}
