package com.example.codeblock.model

sealed class Expression {
    abstract fun evaluate(context: ExecutionContext): Int
    data class Number(val value: Int) : Expression() {
        override fun evaluate(context: ExecutionContext) = value
    }

    data class Variable(val name: String) : Expression() {
        override fun evaluate(context: ExecutionContext): Int {
            return context.getVariable(name)
        }
    }
    data class Binary(
        val left: Expression,
        val operator: Operator,
        val right: Expression
    ) : Expression() {
        override fun evaluate(context: ExecutionContext): Int {
            val leftVal = left.evaluate(context)
            val rightVal = right.evaluate(context)

            return when (operator) {
                Operator.ADD -> leftVal + rightVal
                Operator.SUB -> leftVal - rightVal
                Operator.MUL -> leftVal * rightVal
                Operator.DIV -> {
                    if (rightVal == 0) throw ArithmeticException("Деление на ноль")
                    leftVal / rightVal
                }
                Operator.MOD -> {
                    if (rightVal == 0) throw ArithmeticException("Деление на ноль")
                    leftVal % rightVal
                }
            }
        }
    }

    data class Group(val expression: Expression) : Expression() {
        override fun evaluate(context: ExecutionContext) = expression.evaluate(context)
    }
}

enum class Operator {
    ADD, SUB, MUL, DIV, MOD
}