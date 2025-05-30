package com.example.codeblock.model

object ExpressionParser {
    private val operatorPrecedence = mapOf(
        "*" to 3,
        "/" to 3,
        "%" to 3,
        "+" to 2,
        "-" to 2
    )

    fun parse(input: String): Expression {
        val tokens = tokenize(input)
        return parseExpression(tokens.iterator())
    }

    private fun tokenize(input: String): List<String> {
        val tokens = mutableListOf<String>()
        var current = StringBuilder()
        var i = 0

        while (i < input.length) {
            when (val c = input[i]) {
                ' ' -> {
                    if (current.isNotEmpty()) {
                        tokens.add(current.toString())
                        current.clear()
                    }
                    i++
                }
                '(', ')' -> {
                    if (current.isNotEmpty()) {
                        tokens.add(current.toString())
                        current.clear()
                    }
                    tokens.add(c.toString())
                    i++
                }
                '+', '-', '*', '/', '%' -> {
                    if (current.isNotEmpty()) {
                        tokens.add(current.toString())
                        current.clear()
                    }
                    tokens.add(c.toString())
                    i++
                }
                else -> {
                    current.append(c)
                    i++
                }
            }
        }

        if (current.isNotEmpty()) {
            tokens.add(current.toString())
        }

        return tokens
    }

    private fun parseExpression(tokenIter: Iterator<String>): Expression {
        var left = parseTerm(tokenIter)

        while (tokenIter.hasNext()) {
            val token = tokenIter.next()
            when {
                token == ")" -> return left
                token in operatorPrecedence -> {
                    val op = when (token) {
                        "+" -> Operator.ADD
                        "-" -> Operator.SUB
                        "*" -> Operator.MUL
                        "/" -> Operator.DIV
                        "%" -> Operator.MOD
                            else -> throw IllegalArgumentException("Неизвестный оператор: $token")
                    }
                    val right = parseTerm(tokenIter)
                    left = Expression.Binary(left, op, right)
                }
                else -> throw IllegalArgumentException("Unexpected token: $token")
            }
        }

        return left
    }

    private fun parseTerm(tokenIter: Iterator<String>): Expression {
        if (!tokenIter.hasNext()) throw IllegalArgumentException("Неожиданный конец выражения")

        val token = tokenIter.next()
        return when {
            token == "(" -> {
                val expr = parseExpression(tokenIter)
                if (tokenIter.hasNext() && tokenIter.next() != ")") {
                    throw IllegalArgumentException("Отсутствует закрывающая скобка")
                }
                Expression.Group(expr)
            }
            isNumber(token) -> Expression.Number(token.toInt())
            isValidVariableName(token) -> Expression.Variable(token)
            else -> throw IllegalArgumentException("Invalid term: $token")
        }
    }

    private fun isNumber(s: String): Boolean = s.toIntOrNull() != null
    private fun isValidVariableName(name: String): Boolean {
        return name.matches(Regex("[a-zA-Z_][a-zA-Z0-9_]*"))
    }
}