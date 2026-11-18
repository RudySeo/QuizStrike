package com.quizstriker.global

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HealthControllerTest {
    @Test
    fun `health endpoint returns UP`() {
        val result = HealthController().health()
        assertEquals("UP", result.status)
        assertEquals("QuizStriker", result.service)
    }
}
