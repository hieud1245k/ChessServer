package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.utils.JsonUtils
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class MessageController(private val messagingTemplate: SimpMessageSendingOperations) {

    @MessageMapping("/add-username")
    fun addUser(@Payload username: String, headerAccessor: SimpMessageHeaderAccessor) {
        headerAccessor.sessionAttributes?.put("username", username)
        messagingTemplate.convertAndSendToUser(username, "/queue/add-username", username)
    }

    @MessageMapping("/go-to-box")
    @SendTo("/queue/go-to-box")
    fun goToBox(@Payload message: String, headerAccessor: SimpMessageHeaderAccessor): String? {
        val chessRequest = JsonUtils.fromJson<ChessRequest>(message) ?: return ""
        chessRequest.reverse()
        val response = JsonUtils.toJson(chessRequest)
        return response
    }
}
