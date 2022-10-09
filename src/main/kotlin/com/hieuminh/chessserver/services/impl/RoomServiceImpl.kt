package com.hieuminh.chessserver.services.impl

import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.repositories.RoomRepository
import com.hieuminh.chessserver.services.RoomService
import net.bytebuddy.implementation.bytecode.Throw
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder

@Service
class RoomServiceImpl(
    private val roomRepository: RoomRepository,
) : RoomService {
    override fun getAll(): List<RoomEntity> {
        return roomRepository.findAll()
    }

    override fun createNew(): RoomEntity {
        val requestAttributes = RequestContextHolder.currentRequestAttributes()
        val roomEntity = RoomEntity()
        roomEntity.playerFirstSessionId = requestAttributes.sessionId
        return roomRepository.save(roomEntity)
    }

    override fun findById(id: Long): RoomEntity {
        val room = roomRepository.findById(id)
        if (room.isEmpty) {
            throw CustomException("Room with id $id is not found!", HttpStatus.NOT_FOUND)
        }
        return room.get()
    }
}
