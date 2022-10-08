package com.hieuminh.chessserver.ws

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class InputNameHandler : TextWebSocketHandler() {
    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val name = message.payload.toString()
        session.attributes[session.id] = name
        session.sendMessage(TextMessage(name))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        session.attributes.remove(session.id)
    }
}
