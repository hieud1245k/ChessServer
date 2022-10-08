package com.hieuminh.chessserver.services.impl

import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.repositories.RoomRepository
import com.hieuminh.chessserver.services.RoomService
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
}
