package com.hieuminh.chessserver.controllers

import com.hieuminh.chessserver.requests.base.BaseRequest
import com.hieuminh.chessserver.services.PlayerService
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/api/players")
class PlayerController(
    private val playerService: PlayerService
) {
    @PostMapping("/name")
    fun saveName(@Param("name") name: String, request: HttpServletRequest): ResponseEntity<BaseRequest> {
        playerService.save(name)
        request.session.setAttribute("username", name)
        return ResponseEntity(BaseRequest(), HttpStatus.CREATED)
    }
}
