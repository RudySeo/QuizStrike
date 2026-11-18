package com.quizstriker.domain.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity {
    @field:CreationTimestamp
    @field:Column(nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime
        protected set
}
