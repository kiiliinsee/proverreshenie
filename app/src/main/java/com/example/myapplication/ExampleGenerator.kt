package com.example.myapplication



import kotlin.random.Random
import kotlin.math.round

data class MathExample(
    val operand1: Int,
    val operand2: Int,
    val operator: String,
    val displayedResult: Double,
    val isCorrect: Boolean
)

object ExampleGenerator {
    private val operators = listOf("+", "-", "*", "/")

    fun generateExample(): MathExample {
        val operand1 = Random.nextInt(10, 100)
        val operand2 = Random.nextInt(10, 100)
        val operator = operators.random()

        val correctResult = when (operator) {
            "+" -> operand1 + operand2.toDouble()
            "-" -> operand1 - operand2.toDouble()
            "*" -> operand1 * operand2.toDouble()
            "/" -> round((operand1 / operand2.toDouble()) * 100) / 100
            else -> 0.0
        }

        val isCorrect = Random.nextBoolean() // 50%

        val displayedResult = if (isCorrect) {
            correctResult
        } else {
            // (±1–10)
            val error = Random.nextInt(1, 10)
            if (Random.nextBoolean()) correctResult + error else correctResult - error
        }

        return MathExample(
            operand1 = operand1,
            operand2 = operand2,
            operator = operator,
            displayedResult = displayedResult,
            isCorrect = isCorrect
        )
    }
}
