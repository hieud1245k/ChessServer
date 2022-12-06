package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.exceptions.CustomException
import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.services.PlayerService
import com.hieuminh.chessserver.services.RoomService
import com.hieuminh.chessserver.utils.AppUtils
import com.hieuminh.chessserver.utils.JsonUtils
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class MessageController(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val roomService: RoomService,
    private val playerService: PlayerService,
) {
    private val random = java.util.Random()

    @MessageMapping("/add-username")
    fun addUser(@Payload username: String, headerAccessor: SimpMessageHeaderAccessor) {
        var response = ""
        try {
            val playerEntity = playerService.save(username)
            response = JsonUtils.toJson(playerEntity)
            headerAccessor.sessionAttributes?.put("username", username)
        } catch (e: CustomException) {
            if (e.httpStatus == HttpStatus.CONFLICT) {
                response = "404"
            }
        } finally {
            messagingTemplate.convertAndSend("/queue/add-username/${AppUtils.getPath(username)}", response)
        }
    }

    @MessageMapping("/go-to-box")
    fun goToBox(@Payload message: String, headerAccessor: SimpMessageHeaderAccessor) {
        val response = roomService.goToBox(message)
        messagingTemplate.convertAndSend("/queue/go-to-box/${response.first}", response.second)
    }

    @MessageMapping("/start-game")
    fun startGame(@Payload message: String) {
        val room = roomService.findById(message.toLong())
        val randomBoolean = random.nextBoolean()
        val firstPlayer = (if (randomBoolean) room.playerFirstId else room.playerSecondId) ?: ""
        messagingTemplate.convertAndSend("/queue/start-game/${room.id}", firstPlayer)
    }

    @MessageMapping("/offline/go-to-box")
    fun goToBoxOffline(@Payload message: String, headerAccessor: SimpMessageHeaderAccessor) {
        val chessRequest = JsonUtils.fromJson<ChessRequest>(message) ?: return
        val chessResponse = playerService.gotoBoxOffline(chessRequest)
        messagingTemplate.convertAndSend(
            "/queue/offline/go-to-box/${chessRequest.roomId}",
            JsonUtils.toJson(chessResponse),
        )
    }
}
