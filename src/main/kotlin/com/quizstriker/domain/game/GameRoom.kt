package com.quizstriker.domain.game

import com.quizstriker.domain.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

@Entity
@Table(name = "game_rooms")
class GameRoom(
    maxPlayers: Int,
    quizCount: Int,
) : BaseTimeEntity() {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "host_participant_id", unique = true)
    var hostParticipant: RoomParticipant? = null
        protected set

    @field:Positive
    @field:Column(nullable = false)
    var maxPlayers: Int = maxPlayers
        protected set

    @field:Positive
    @field:Column(nullable = false)
    var quizCount: Int = quizCount
        protected set

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false, length = 20)
    var status: GameRoomStatus = GameRoomStatus.WAITING
        protected set

    @field:Column
    var startedAt: LocalDateTime? = null
        protected set

    @field:Column
    var endedAt: LocalDateTime? = null
        protected set

    init {
        require(maxPlayers > 0) { "Maximum players must be positive." }
        require(quizCount > 0) { "Quiz count must be positive." }
    }

    fun assignHost(participant: RoomParticipant) {
        require(participant.belongsTo(this)) { "Host participant must belong to this room." }
        hostParticipant = participant
    }

    fun start(startedAt: LocalDateTime = LocalDateTime.now()) {
        check(status == GameRoomStatus.WAITING) { "Only a waiting room can be started." }
        check(hostParticipant != null) { "A host must be assigned before starting the room." }
        status = GameRoomStatus.IN_PROGRESS
        this.startedAt = startedAt
    }

    fun finish(endedAt: LocalDateTime = LocalDateTime.now()) {
        check(status == GameRoomStatus.IN_PROGRESS) { "Only an in-progress room can be finished." }
        status = GameRoomStatus.FINISHED
        this.endedAt = endedAt
    }
}
