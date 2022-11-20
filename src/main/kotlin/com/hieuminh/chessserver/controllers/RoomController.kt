package com.hieuminh.chessserver.controllers

import com.google.gson.Gson
import com.hieuminh.chessserver.entities.RoomEntity
import com.hieuminh.chessserver.services.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val roomService: RoomService,
    private val messagingTemplate: SimpMessageSendingOperations
) {

    @GetMapping("/")
    fun getAll(): ResponseEntity<List<RoomEntity>> {
        return ResponseEntity.ok(roomService.getAll())
    }

    @PostMapping("/")
    fun createNew(@RequestParam("name") name: String): ResponseEntity<RoomEntity> {
        return ResponseEntity(roomService.createNew(name), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getRoom(@PathVariable id: Long): ResponseEntity<RoomEntity> {
        return ResponseEntity.ok(roomService.findById(id))
    }

    @PutMapping("/{id}")
    fun joinRoom(@PathVariable id: Long, @RequestParam("name") name: String): ResponseEntity<RoomEntity> {
        val room = roomService.joinRoom(id, name)
        messagingTemplate.convertAndSend("/queue/join-room/${room.id}", Gson().toJson(room))
        return ResponseEntity.ok(room)
    }

    @PutMapping("/leave")
    fun leaveRoom(@RequestBody room: RoomEntity): ResponseEntity<RoomEntity> {
        val roomResponse = roomService.leaveRoom(room)
        messagingTemplate.convertAndSend("/queue/join-room/${roomResponse.id}", Gson().toJson(roomResponse))
        return ResponseEntity.ok(roomResponse)
    }

    @PostMapping("/start-offline-game")
    fun startOfflineGame(@RequestParam("name") name: String): ResponseEntity<RoomEntity> {
        val roomResponse = roomService.startOfflineGame(name)
        return ResponseEntity.ok(roomResponse)
    }

    @PutMapping("/play-now")
    fun playNow(@RequestParam("name") name: String): ResponseEntity<RoomEntity> {
        val roomResponse = roomService.playNow(name)
        messagingTemplate.convertAndSend("/queue/join-room/${roomResponse.id}", Gson().toJson(roomResponse))
        return ResponseEntity.ok(roomResponse)
    }
}
