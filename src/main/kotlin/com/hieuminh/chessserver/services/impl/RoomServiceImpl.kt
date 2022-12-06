package com.hieuminh.chessserver.services.impl

import com.google.gson.Gson
import com.hieuminh.chessserver.boardGame.BoardSpec.Companion.toBoard
import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.exceptions.CustomException
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
    private val messagingTemplate: SimpMessageSendingOperations,
) : RoomService {
    private val random = java.util.Random()

    override fun getAll(): List<RoomEntity> {
        return roomRepository.findAllByDeletedAtNullAndIsOnlineTrue()
    }

    override fun createNew(playerId: Long): RoomEntity {
        val roomEntity = RoomEntity()
        roomEntity.playerFirstId = playerId
        return roomRepository.save(roomEntity)
    }

    override fun findById(roomId: Long): RoomEntity {
        val room = roomRepository.findByIdAndDeletedAtNull(roomId)
        if (room.isEmpty) {
            throw CustomException("Room with id $roomId is not found!", HttpStatus.NOT_FOUND)
        }
        return room.get()
    }

    override fun joinRoom(roomId: Long, playerId: Long): RoomEntity {
        val room = findById(roomId)
        if (room.playerFirstId != null && room.playerSecondId != null) {
            throw CustomException("Room with id $roomId is full player!", HttpStatus.CONFLICT)
        }
        if (room.playerFirstId != null) {
            room.playerSecondId = playerId
        } else {
            room.playerFirstId = playerId
        }
        return roomRepository.save(room)
    }

    override fun removeByPlayerId(playerId: Long) {
        val playerEntities = roomRepository.findAllByPlayerFirstIdOrPlayerSecondId(playerId, playerId)
        playerEntities.forEach { roomEntity ->
            when {
                playerId == roomEntity.playerFirstId && roomEntity.playerSecondId != null -> {
                    roomEntity.playerFirstId = null
                }
                playerId == roomEntity.playerSecondId && roomEntity.playerFirstId != null -> {
                    roomEntity.playerSecondId = null
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
        if (roomEntity.id == 0L || roomEntity.playerFirstId != null && roomEntity.playerSecondId != null) {
            throw CustomException("Data is invalid!", HttpStatus.BAD_REQUEST)
        }
        if (roomEntity.playerFirstId == null && roomEntity.playerSecondId == null || !roomEntity.isOnline) {
            roomEntity.deletedAt = LocalDate.now()
        }
        return roomRepository.save(roomEntity)
    }

    override fun startOfflineGame(playerId: Long): RoomEntity {
        val room = RoomEntity()
        room.playerFirstId = playerId
        room.isOnline = false
        room.firstPlay = playerId
        return roomRepository.save(room)
    }

    override fun save(roomEntity: RoomEntity): RoomEntity {
        return roomRepository.save(roomEntity)
    }

    override fun playNow(playerId: Long): RoomEntity {
        val optional =
            roomRepository.findTop1ByDeletedAtNullAndIsOnlineTrueAndPlayerFirstIdNullOrPlayerSecondIdNull()
        if (optional.isEmpty) {
            throw CustomException("Bad request", HttpStatus.BAD_REQUEST)
        }
        val roomEntity = optional.get()
        when {
            roomEntity.playerFirstId == null && roomEntity.playerSecondId == null -> {
                throw CustomException("Bad request", HttpStatus.BAD_REQUEST)
            }
            roomEntity.playerFirstId == null -> {
                roomEntity.playerFirstId = playerId
            }
            roomEntity.playerSecondId == null -> {
                roomEntity.playerSecondId = playerId
            }
        }
        return roomRepository.save(roomEntity)
    }

    override fun goToBox(message: String): Pair<Long, String> {
        val chessRequest =
            JsonUtils.fromJson<ChessRequest>(message) ?: throw CustomException("Bad request", HttpStatus.BAD_REQUEST)
        val room = findById(chessRequest.roomId ?: 0)
        val board = room.boardString?.toBoard()
        chessRequest.run {
            try {
                board?.play(from!!.toSquare(), to!!.toSquare())
            } catch (e: Exception) {
                e.printStackTrace()
                // TODO: Handle later
            }
        }
        room.boardString = board?.toPrettyString()
        roomRepository.save(room)
        chessRequest.reverse()
        return Pair(room.id, JsonUtils.toJson(chessRequest))
    }
}
