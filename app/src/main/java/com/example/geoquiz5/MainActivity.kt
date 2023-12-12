package com.example.geoquiz5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : ComponentActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var mainPeekButton: Button
    private lateinit var questionTextView: TextView
    private var score: Int = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)
        mainPeekButton = findViewById(R.id.peek_main)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            summarize()
            next()
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            summarize()
            next()
        }
        nextButton.setOnClickListener {
            next()
        }
        backButton.setOnClickListener {
            quizViewModel.moveBack()
            updateQuestion()
        }
        mainPeekButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PeekActivity::class.java)
            intent.putExtra("answer", quizViewModel.currentQuestionAnswer.toString())
            startActivity(intent)
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            score++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun summarize() {
        if (quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            val percentage = (score.toFloat() / quizViewModel.questionBank.size * 100).toInt()
            Toast.makeText(
                this,
                "Вы ответили правильно на $percentage% вопросов",
                Toast.LENGTH_LONG
            ).show()
            score = 0
        }
    }

    private fun next() {
        quizViewModel.moveToNext()
        updateQuestion()
    }
}