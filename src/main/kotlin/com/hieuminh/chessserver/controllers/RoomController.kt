package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.services.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
class RoomController(private val roomService: RoomService) {

    @GetMapping("/")
    fun getAll(): ResponseEntity<List<RoomEntity>> {
        return ResponseEntity.ok(roomService.getAll())
    }

    @PostMapping("/")
    fun createNew(): ResponseEntity<RoomEntity> {
        return ResponseEntity(roomService.createNew(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getRoom(@PathVariable id: Long): ResponseEntity<RoomEntity> {
        return ResponseEntity.ok(roomService.findById(id))
    }
}
