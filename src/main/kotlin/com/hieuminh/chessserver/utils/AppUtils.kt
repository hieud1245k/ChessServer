package com.hieuminh.chessserver.utils

object AppUtils {
    fun getPath(username: String): String {
        return username.replace("\\s+".toRegex(), "")
    }
}
