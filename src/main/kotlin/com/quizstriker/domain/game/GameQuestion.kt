package com.quizstriker.domain.game

import com.quizstriker.domain.common.BaseTimeEntity
import com.quizstriker.domain.quiz.Quiz
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

@Entity
@Table(
    name = "game_questions",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_game_question_room_number",
            columnNames = ["room_id", "question_number"],
        ),
    ],
)
class GameQuestion(
    room: GameRoom,
    quiz: Quiz,
    questionNumber: Int,
) : BaseTimeEntity() {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "room_id", nullable = false)
    var room: GameRoom = room
        protected set

    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "quiz_id", nullable = false)
    var quiz: Quiz = quiz
        protected set

    @field:Positive
    @field:Column(name = "question_number", nullable = false)
    var questionNumber: Int = questionNumber
        protected set

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "solver_id")
    var solver: RoomParticipant? = null
        protected set

    @field:Column
    var startedAt: LocalDateTime? = null
        protected set

    init {
        require(questionNumber > 0) { "Question number must be positive." }
    }

    fun start(startedAt: LocalDateTime = LocalDateTime.now()) {
        check(this.startedAt == null) { "Question has already started." }
        this.startedAt = startedAt
    }

    fun markSolvedBy(participant: RoomParticipant) {
        check(startedAt != null) { "Question must be started before it can be solved." }
        check(solver == null) { "Question already has a solver." }
        require(participant.belongsTo(room)) { "Solver must belong to the question's room." }
        solver = participant
    }

    internal fun isInSameRoom(participant: RoomParticipant): Boolean = participant.belongsTo(room)
}
