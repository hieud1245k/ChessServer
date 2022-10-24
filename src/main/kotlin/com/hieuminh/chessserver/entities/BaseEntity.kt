package com.hieuminh.chessserver.entities

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @CreatedDate
    var createdAt: LocalDate? = null

    var deletedAt: LocalDate? = null
}
