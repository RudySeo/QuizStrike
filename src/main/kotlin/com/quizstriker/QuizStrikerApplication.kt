package com.quizstriker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizStrikerApplication

fun main(args: Array<String>) {
    runApplication<QuizStrikerApplication>(*args)
}
