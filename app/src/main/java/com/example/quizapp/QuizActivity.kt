package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizActivity : AppCompatActivity() {
    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        userName = intent.getStringExtra("USER_NAME") ?: "User"
        questions = QuestionBank.questions.shuffled()

        displayQuestion()

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            checkAnswer()
        }
    }

    private fun displayQuestion() {
        if (currentQuestionIndex >= questions.size) {
            endQuiz()
            return
        }
        // Update progress bar
        val progress = ((currentQuestionIndex + 1) * 100) / questions.size
        findViewById<ProgressBar>(R.id.progressBar).progress = progress

        // Update progress text
        findViewById<TextView>(R.id.tvProgress).text =
            "${currentQuestionIndex + 1}/${questions.size}"

        val currentQuestion = questions[currentQuestionIndex]
        findViewById<TextView>(R.id.tvQuestion).text = currentQuestion.questionText
        findViewById<RadioButton>(R.id.option1).text = currentQuestion.option1
        findViewById<RadioButton>(R.id.option2).text = currentQuestion.option2
        findViewById<RadioButton>(R.id.option3).text = currentQuestion.option3
        findViewById<RadioButton>(R.id.option4).text = currentQuestion.option4

        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
    }

    private fun checkAnswer() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val selectedId = radioGroup.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        val selectedAnswerIndex = when (selectedId) {
            R.id.option1 -> 1
            R.id.option2 -> 2
            R.id.option3 -> 3
            R.id.option4 -> 4
            else -> 0
        }

        val currentQuestion = questions[currentQuestionIndex]
        if (selectedAnswerIndex == currentQuestion.correctAnswer) {
            score++
            selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
        } else {
            selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_answer))
            val correctRadioButton = when (currentQuestion.correctAnswer) {
                1 -> findViewById<RadioButton>(R.id.option1)
                2 -> findViewById<RadioButton>(R.id.option2)
                3 -> findViewById<RadioButton>(R.id.option3)
                4 -> findViewById<RadioButton>(R.id.option4)
                else -> null
            }
            correctRadioButton?.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            currentQuestionIndex++
            resetRadioButtons()
            displayQuestion()
        }, 1000)
    }

    private fun resetRadioButtons() {
        listOf(R.id.option1, R.id.option2, R.id.option3, R.id.option4).forEach { id ->
            findViewById<RadioButton>(id).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun endQuiz() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("SCORE", score)
            putExtra("TOTAL_QUESTIONS", questions.size)
            putExtra("USER_NAME", userName)
        }
        startActivity(intent)
        finish()
    }
}