package com.hieuminh.chessserver.ws

import com.hieuminh.chessserver.requests.ChessRequest
import com.hieuminh.chessserver.utils.JsonUtils
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class ChessmanActionHandler : TextWebSocketHandler() {
    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val textMessage = message.payload.toString()
        if (textMessage.contains("__")) {
            sectionMap[textMessage] = session
            return
        }
        val chessRequest = JsonUtils.fromJson<ChessRequest>(textMessage) ?: return
        chessRequest.reverse()
        val request = JsonUtils.toJson(chessRequest)
        val rivalSession = sectionMap["${chessRequest.playerName}__"] ?: return
        rivalSession.sendMessage(TextMessage(request))
    }

    companion object {
        private val sectionMap: HashMap<String, WebSocketSession> = hashMapOf()
    }
}
