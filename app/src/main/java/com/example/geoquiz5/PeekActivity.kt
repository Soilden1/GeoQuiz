package com.example.geoquiz5

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class PeekActivity : ComponentActivity() {
    private lateinit var peekQuestionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var peekButton: Button
    private lateinit var answer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peek)

        peekQuestionTextView = findViewById(R.id.peek_question_text_view)
        answerTextView = findViewById(R.id.answer)
        peekButton = findViewById(R.id.peek_button)
        peekQuestionTextView.setText(R.string.peek_message)
        answer = this.intent.extras?.getString("answer").toString()

        peekButton.setOnClickListener { view: View ->
            showAnswer()
        }
    }

    private fun showAnswer() {
        if (answer == "true") answer = "ПРАВДА"
        if (answer == "false") answer = "ЛОЖЬ"
        answerTextView.text = answer
        android.os.Handler().postDelayed({ finish() }, 4000)
    }
}