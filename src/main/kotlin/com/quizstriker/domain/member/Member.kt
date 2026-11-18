package com.quizstriker.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "members")
class Member(
    nickname: String,
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @field:NotBlank
    @field:Size(max = 50)
    @field:Column(nullable = false, unique = true, length = 50)
    var nickname: String = nickname
        protected set

    init {
        require(nickname.isNotBlank()) { "Nickname must not be blank." }
        require(nickname.length <= 50) { "Nickname must be at most 50 characters." }
    }

    fun changeNickname(nickname: String) {
        require(nickname.isNotBlank()) { "Nickname must not be blank." }
        require(nickname.length <= 50) { "Nickname must be at most 50 characters." }
        this.nickname = nickname
    }
}
