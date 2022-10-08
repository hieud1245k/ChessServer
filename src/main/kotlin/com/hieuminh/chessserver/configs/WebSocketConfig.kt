package com.hieuminh.chessserver.configs

import com.hieuminh.chessserver.ws.ChessmanActionHandler
import com.hieuminh.chessserver.ws.InputNameHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(getInputNameHandler(), "/name")
            .addHandler(getChessmanActionHandler(), "/chessman")
    }

    @Bean
    fun getInputNameHandler(): InputNameHandler {
        return InputNameHandler()
    }

    @Bean
    fun getChessmanActionHandler(): ChessmanActionHandler {
        return ChessmanActionHandler()
    }
}
