package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.services.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
