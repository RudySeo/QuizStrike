package com.quizstriker.global

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/api/health")
    fun health() = HealthResponse(status = "UP", service = "QuizStriker")
}

data class HealthResponse(val status: String, val service: String)
