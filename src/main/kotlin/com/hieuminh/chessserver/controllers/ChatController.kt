package com.hieuminh.chessserver.controllers

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin("*")
@Controller
class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload chatMessage: String): String {
        return chatMessage
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    fun addUser(
        @Payload chatMessage: String,
        headerAccessor: SimpMessageHeaderAccessor
    ): String {
        headerAccessor.sessionAttributes?.put("username", chatMessage)
        return chatMessage
    }
}
