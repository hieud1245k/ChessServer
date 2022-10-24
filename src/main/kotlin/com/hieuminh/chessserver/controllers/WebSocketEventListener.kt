package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.services.PlayerService
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val playerService: PlayerService,
) {

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent?) {
        logger.info("Received a new web socket connection")
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val headerAccessor: StompHeaderAccessor = StompHeaderAccessor.wrap(event.message)
        val username = headerAccessor.sessionAttributes?.get("username") as? String ?: return
        playerService.removeByName(username)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)
    }
}
