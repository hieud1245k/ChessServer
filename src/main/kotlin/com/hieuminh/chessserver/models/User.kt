package com.hieuminh.chessserver.models

import java.security.Principal

class User(private val name: String) : Principal {

    override fun getName(): String {
        return name
    }
}
