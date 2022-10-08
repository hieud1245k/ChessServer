package com.hieuminh.chessserver.exceptions

import org.springframework.http.HttpStatus

class CustomException(override val message: String, val httpStatus: HttpStatus) : RuntimeException() {

    companion object {
        private const val serialVersionUID = 1L
    }
}
