package com.hieuminh.chessserver.exceptions

import org.springframework.http.HttpStatus

class CustomException(val httpStatus: HttpStatus, override val message: String = "") : RuntimeException() {

    companion object {
        private const val serialVersionUID = 1L
    }
}
