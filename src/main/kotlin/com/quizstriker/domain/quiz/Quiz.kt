package com.quizstriker.domain.quiz

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "quizzes")
class Quiz(
    question: String,
    answer: String,
    difficulty: QuizDifficulty,
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @field:NotBlank
    @field:Column(nullable = false, columnDefinition = "TEXT")
    var question: String = question
        protected set

    @field:NotBlank
    @field:Size(max = 255)
    @field:Column(nullable = false, length = 255)
    var answer: String = answer
        protected set

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false, length = 20)
    var difficulty: QuizDifficulty = difficulty
        protected set

    init {
        require(question.isNotBlank()) { "Question must not be blank." }
        require(answer.isNotBlank()) { "Answer must not be blank." }
        require(answer.length <= 255) { "Answer must be at most 255 characters." }
    }
}
