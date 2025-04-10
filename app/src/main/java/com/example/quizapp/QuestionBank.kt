package com.example.quizapp

object QuestionBank {
    val questions = listOf(
        Question(
            id = 1,
            questionText = "What is the capital of France?",
            option1 = "Berlin",
            option2 = "Madrid",
            option3 = "Paris",
            option4 = "Rome",
            correctAnswer = 3
        ),
        Question(
            id = 2,
            questionText = "Which planet is known as the Red Planet?",
            option1 = "Venus",
            option2 = "Mars",
            option3 = "Jupiter",
            option4 = "Saturn",
            correctAnswer = 2
        ),
        Question(
            id = 3,
            questionText = "What is 2+2?",
            option1 = "3",
            option2 = "4",
            option3 = "5",
            option4 = "6",
            correctAnswer = 2
        )
    )
}