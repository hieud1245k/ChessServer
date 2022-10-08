package com.hieuminh.chessserver.exceptions

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandlerController {
    @Bean
    fun errorAttributes(): ErrorAttributes {
        // Hide exception field in the return object
        return object : DefaultErrorAttributes() {
            override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions): Map<String, Any> {
                return super.getErrorAttributes(
                    webRequest,
                    ErrorAttributeOptions.defaults().excluding(ErrorAttributeOptions.Include.EXCEPTION)
                )
            }
        }
    }

    @ExceptionHandler(CustomException::class)
    @Throws(IOException::class)
    fun handleCustomException(res: HttpServletResponse, ex: CustomException) {
        res.sendError(ex.httpStatus.value(), ex.message)
    }

    @ExceptionHandler(AccessDeniedException::class)
    @Throws(
        IOException::class
    )
    fun handleAccessDeniedException(res: HttpServletResponse) {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied")
    }

    @ExceptionHandler(Exception::class)
    @Throws(IOException::class)
    fun handleException(res: HttpServletResponse) {
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong")
    }
}
