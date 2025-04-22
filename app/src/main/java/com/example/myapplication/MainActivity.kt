package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




import android.os.SystemClock
import android.widget.*

import kotlin.math.round
import com.example.myapplication.ExampleGenerator
import com.example.myapplication.MathExample

class MainActivity : AppCompatActivity() {


    private lateinit var txtFirstOperand: TextView
    private lateinit var txtTwoOperand: TextView
    private lateinit var txtOperation: TextView
    private lateinit var txtResult: TextView
    private lateinit var txtAllExamples: TextView
    private lateinit var txtNumberRight: TextView
    private lateinit var txtNumberWrong: TextView
    private lateinit var txtPercentageCorrectAnswers: TextView
    private lateinit var txtTimeMin: TextView
    private lateinit var txtTimeMax: TextView
    private lateinit var txtTimeAverage: TextView
    private lateinit var txtView7: TextView

    private lateinit var btnStart: Button
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button

    private var currentExample: MathExample? = null
    private var startTime: Long = 0

    private var rightAnswers = 0
    private var wrongAnswers = 0
    private val responseTimes = mutableListOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        btnStart.setOnClickListener {
            currentExample = ExampleGenerator.generateExample()
            showExample(currentExample!!)
            enableAnswerButtons(true)
            startTime = SystemClock.elapsedRealtime()
        }

        btnTrue.setOnClickListener {
            handleAnswer(true)
        }

        btnFalse.setOnClickListener {
            handleAnswer(false)
        }
    }

    private fun initViews() {
        txtFirstOperand = findViewById(R.id.txtFirstOperand)
        txtTwoOperand = findViewById(R.id.txtTwoOperand)
        txtOperation = findViewById(R.id.txtOperation)
        txtResult = findViewById(R.id.txtResult)
        txtAllExamples = findViewById(R.id.txtAllExamples)
        txtNumberRight = findViewById(R.id.txtNumberRight)
        txtNumberWrong = findViewById(R.id.txtNumberWrong)
        txtPercentageCorrectAnswers = findViewById(R.id.txtPercentageCorrectAnswers)
        txtTimeMin = findViewById(R.id.txtTimeMin)
        txtTimeMax = findViewById(R.id.txtTimeMax)
        txtTimeAverage = findViewById(R.id.txtTimeAverage)
        txtView7 = findViewById(R.id.txtView7)

        btnStart = findViewById(R.id.btnStart)
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
    }

    private fun showExample(example: MathExample) {
        txtFirstOperand.text = example.operand1.toString()
        txtTwoOperand.text = example.operand2.toString()
        txtOperation.text = example.operator
        txtResult.text = if (example.operator == "/") {
            String.format("%.2f", example.displayedResult)
        } else {
            example.displayedResult.toInt().toString()
        }
        txtView7.text = ""
    }

    private fun handleAnswer(userAnswer: Boolean) {
        val endTime = SystemClock.elapsedRealtime()
        val duration = (endTime - startTime) / 1000 // секунды
        responseTimes.add(duration)

        val correct = currentExample?.isCorrect == userAnswer

        if (correct) {
            rightAnswers++
            txtView7.text = "ПРАВИЛЬНО"
        } else {
            wrongAnswers++
            txtView7.text = "НЕ ПРАВИЛЬНО"
        }

        updateStats()
        enableAnswerButtons(false)
    }

    private fun enableAnswerButtons(enable: Boolean) {
        btnTrue.isEnabled = enable
        btnFalse.isEnabled = enable
    }



    private fun updateStats() {
        val total = rightAnswers + wrongAnswers
        val percent = if (total > 0) round((rightAnswers.toDouble() / total) * 10000) / 100 else 0.0

        txtAllExamples.text = "Итого проверено примеров: $total"
        txtNumberRight.text = "Правильно: $rightAnswers"
        txtNumberWrong.text = "Неправильно: $wrongAnswers"
        txtPercentageCorrectAnswers.text = "$percent%"

        if (responseTimes.isNotEmpty()) {
            val min = responseTimes.minOrNull() ?: 0
            val max = responseTimes.maxOrNull() ?: 0
            val average = responseTimes.average()
            txtTimeMin.text = "Минимум: $min"
            txtTimeMax.text = "Максимум: $max"
            txtTimeAverage.text = "Среднее: %.2f".format(average)
        }
    }
}
