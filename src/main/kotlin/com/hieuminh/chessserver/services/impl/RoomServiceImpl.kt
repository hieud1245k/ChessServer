package com.hieuminh.chessserver.services.impl

import com.google.gson.Gson
import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.RoomRepository
import com.hieuminh.chessserver.services.RoomService
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import java.time.LocalDate

@Service
class RoomServiceImpl(
    private val roomRepository: RoomRepository,
    private val messagingTemplate: SimpMessageSendingOperations,
) : RoomService {
    override fun getAll(): List<RoomEntity> {
        return roomRepository.findAllByDeletedAtNull()
    }

    override fun createNew(name: String): RoomEntity {
        val requestAttributes = RequestContextHolder.currentRequestAttributes()
        val roomEntity = RoomEntity()
        roomEntity.playerFirstName = name
        return roomRepository.save(roomEntity)
    }

    override fun findById(id: Long): RoomEntity {
        val room = roomRepository.findById(id)
        if (room.isEmpty) {
            throw CustomException("Room with id $id is not found!", HttpStatus.NOT_FOUND)
        }
        return room.get()
    }

    override fun joinRoom(id: Long, name: String): RoomEntity {
        val roomOptional = roomRepository.findById(id)
        if (roomOptional.isEmpty) {
            throw CustomException("Room with id $id is not found!", HttpStatus.NOT_FOUND)
        }
        val room = roomOptional.get()
        room.playerSecondName = name
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
}
