package com.quizstriker.domain.game

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.PositiveOrZero
import java.time.LocalDateTime

@Entity
@Table(
    name = "quiz_results",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_quiz_result_question_participant",
            columnNames = ["game_question_id", "room_participant_id"],
        ),
    ],
)
class QuizResult(
    gameQuestion: GameQuestion,
    roomParticipant: RoomParticipant,
    wrongCount: Int = 0,
    resultType: QuizResultType,
    earnedScore: Int = 0,
    completedAt: LocalDateTime = LocalDateTime.now(),
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "game_question_id", nullable = false)
    var gameQuestion: GameQuestion = gameQuestion
        protected set

    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "room_participant_id", nullable = false)
    var roomParticipant: RoomParticipant = roomParticipant
        protected set

    @field:PositiveOrZero
    @field:Column(nullable = false)
    var wrongCount: Int = wrongCount
        protected set

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false, length = 20)
    var resultType: QuizResultType = resultType
        protected set

    @field:PositiveOrZero
    @field:Column(nullable = false)
    var earnedScore: Int = earnedScore
        protected set

    @field:Column(nullable = false)
    var completedAt: LocalDateTime = completedAt
        protected set

    init {
        require(wrongCount >= 0) { "Wrong count must not be negative." }
        require(earnedScore >= 0) { "Earned score must not be negative." }
        require(gameQuestion.isInSameRoom(roomParticipant)) {
            "Result participant must belong to the question's room."
        }
    }
}
