package com.hieuminh.chessserver.ws

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class ChessmanActionHandler : TextWebSocketHandler() {

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        print(message.payload.toString())
        session.sendMessage(TextMessage("dsfjnaksfdn Hieu"))
    }
}
