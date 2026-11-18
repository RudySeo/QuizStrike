package com.quizstriker.domain.game

import com.quizstriker.domain.common.BaseTimeEntity
import com.quizstriker.domain.member.Member
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

@Entity
@Table(
    name = "room_participants",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_room_participant_room_member",
            columnNames = ["room_id", "member_id"],
        ),
    ],
)
class RoomParticipant(
    room: GameRoom,
    member: Member,
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
    @field:JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    @field:PositiveOrZero
    @field:Column(nullable = false)
    var score: Int = 0
        protected set

    @field:Enumerated(EnumType.STRING)
    @field:Column(nullable = false, length = 20)
    var status: RoomParticipantStatus = RoomParticipantStatus.ACTIVE
        protected set

    fun addScore(points: Int) {
        require(points >= 0) { "Score increment must not be negative." }
        score += points
    }

    fun leave() {
        status = RoomParticipantStatus.LEFT
    }

    internal fun belongsTo(gameRoom: GameRoom): Boolean =
        room === gameRoom || (room.id != null && room.id == gameRoom.id)
}
