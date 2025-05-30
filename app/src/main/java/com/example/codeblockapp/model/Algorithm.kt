import java.util.Stack

private fun Stack<MutableMap<String, Int>>.peekOrEmpty() = if (isNotEmpty()) peek() else mutableMapOf()

class ExpressionEvaluator(
    initialVariables: Map<String, Int> = mutableMapOf(),
    private val forbiddenVariables: Set<String> = emptySet()
) {
    private val contextStack = Stack<MutableMap<String, Int>>().apply {
        push(mutableMapOf<String, Int>().apply { putAll(initialVariables) })
    }

    fun pushContext(newVariables: Map<String, Int> = mutableMapOf()) {
        contextStack.push((contextStack.peekOrEmpty() + newVariables).toMutableMap())
    }

    fun getGlobalContext(): MutableMap<String, Int> {
        return contextStack.first()
    }

    fun getAllContexts(): List<MutableMap<String, Int>> {
        return contextStack.toList()
    }

    fun pushContext() {
        contextStack.push(mutableMapOf())
    }

    fun popContext() {
        if (contextStack.size > 1) {
            contextStack.pop()
        }
    }

    fun currentContext(): MutableMap<String, Int> = contextStack.peek()

    fun hasVariable(name: String): Boolean {
        return contextStack.any { it.containsKey(name) }
    }

    fun getVariable(name: String): Int? {
        for (context in contextStack.reversed()) {
            context[name]?.let { return it }
        }
        return null
    }
    fun evaluate(expression: String): Int {
        try {
            if (expression.isBlank()) {
                throw IllegalArgumentException("Empty expression")
            }

            checkForbiddenVariables(expression)
            validateExpressionCharacters(expression)

            val rpn = convertToRPN(expression)
            return evaluateRPN(rpn)
        } catch (e: Exception) {
            throw IllegalArgumentException("${e.message}")
        }
    }

    private fun validateExpressionCharacters(expr: String) {
        val allowedChars = setOf('+', '-', '*', '/', '%', '(', ')', ' ') +
                ('0'..'9') + ('a'..'z') + ('A'..'Z') + '_'

        expr.forEach { char ->
            if (!allowedChars.contains(char)) {
                throw IllegalArgumentException("Invalid character '$char' in expression")
            }
        }
    }

    fun checkForbiddenVariables(expr: String) {
        val variableNames = mutableSetOf<String>()
        var i = 0

        while (i < expr.length) {
            if (expr[i].isLetter()) {
                val varName = StringBuilder()
                while (i < expr.length && (expr[i].isLetterOrDigit() || expr[i] == '_')) {
                    varName.append(expr[i++])
                }
                val name = varName.toString()
                if (forbiddenVariables.contains(name)) {
                    throw IllegalArgumentException("Cannot use variable '$name' in its own declaration")
                }
                variableNames.add(name)
            } else {
                i++
            }
        }
    }

    private fun convertToRPN(expr: String): List<Token> {
        val output = mutableListOf<Token>()
        val operators = Stack<Token>()
        var i = 0

        while (i < expr.length) {
            when (val c = expr[i]) {
                ' ' -> { i++ }
                '(' -> {
                    operators.push(Token(c.toString(), TokenType.LEFT_PAREN))
                    i++
                }
                ')' -> {
                    while (operators.isNotEmpty() && operators.peek().type != TokenType.LEFT_PAREN) {
                        output.add(operators.pop())
                    }
                    if (operators.isEmpty() || operators.peek().type != TokenType.LEFT_PAREN) {
                        throw IllegalArgumentException("Mismatched parentheses")
                    }
                    operators.pop()
                    i++
                }
                in '0'..'9' -> {
                    val num = StringBuilder()
                    while (i < expr.length && expr[i].isDigit()) {
                        num.append(expr[i++])
                    }
                    output.add(Token(num.toString(), TokenType.NUMBER))
                }
                else -> {
                    if (c in "+-*/%") {
                        val op = Token(c.toString(), TokenType.OPERATOR)
                        while (operators.isNotEmpty() &&
                            operators.peek().type == TokenType.OPERATOR &&
                            getPrecedence(operators.peek().value) >= getPrecedence(op.value)
                        ) {
                            output.add(operators.pop())
                        }
                        operators.push(op)
                        i++
                    } else if (c.isLetter()) {
                        val varName = StringBuilder()
                        while (i < expr.length && (expr[i].isLetterOrDigit() || expr[i] == '_')) {
                            varName.append(expr[i++])
                        }
                        output.add(Token(varName.toString(), TokenType.VARIABLE))
                    } else {
                        throw IllegalArgumentException("Unexpected character '$c'")
                    }
                }
            }
        }

        while (operators.isNotEmpty()) {
            if (operators.peek().type == TokenType.LEFT_PAREN) {
                throw IllegalArgumentException("Mismatched parentheses")
            }
            output.add(operators.pop())
        }

        return output
    }

    private fun evaluateRPN(rpn: List<Token>): Int {
        val stack = Stack<Int>()

        for (token in rpn) {
            try {
                when (token.type) {
                    TokenType.NUMBER -> stack.push(token.value.toInt())
                    TokenType.VARIABLE -> {
                        // Ищем переменную во всех контекстах (от текущего к глобальному)
                        val value = findVariableInContexts(token.value)
                            ?: throw IllegalArgumentException("Variable '${token.value}' not found")
                        stack.push(value)
                    }
                    TokenType.OPERATOR -> {
                        if (stack.size < 2) throw IllegalArgumentException("Not enough operands")
                        val b = stack.pop()
                        val a = stack.pop()
                        stack.push(applyOperator(a, b, token.value))
                    }
                    else -> throw IllegalStateException("Invalid token type")
                }
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid number format")
            } catch (e: ArithmeticException) {
                throw IllegalArgumentException("Arithmetic error: ${e.message}")
            }
        }

        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid expression format")
        }

        return stack.pop()
    }

    private fun findVariableInContexts(name: String): Int? {
        for (context in contextStack.reversed()) {
            context[name]?.let { return it }
        }
        return null
    }

    private fun applyOperator(a: Int, b: Int, op: String): Int {
        return try {
            when (op) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> {
                    if (b == 0) throw ArithmeticException("Division by zero")
                    a / b
                }
                "%" -> {
                    if (b == 0) throw ArithmeticException("Modulo by zero")
                    a % b
                }
                else -> throw IllegalArgumentException("Unknown operator: $op")
            }
        } catch (e: ArithmeticException) {
            throw IllegalArgumentException("Arithmetic error: ${e.message}")
        }
    }

    private fun getPrecedence(op: String): Int = when (op) {
        "+", "-" -> 1
        "*", "/", "%" -> 2
        else -> 0
    }

    fun evaluateCondition(leftExpr: String, operation: String, rightExpr: String): Boolean {
        val leftValue = evaluate(leftExpr)
        val rightValue = evaluate(rightExpr)

        return when (operation) {
            ">" -> leftValue > rightValue
            "<" -> leftValue < rightValue
            "==" -> leftValue == rightValue
            "!=" -> leftValue != rightValue
            ">=" -> leftValue >= rightValue
            "<=" -> leftValue <= rightValue
            else -> throw IllegalArgumentException("Unknown operation: $operation")
        }
    }

    private sealed class TokenType {
        object NUMBER : TokenType()
        object VARIABLE : TokenType()
        object OPERATOR : TokenType()
        object LEFT_PAREN : TokenType()
    }

    private data class Token(val value: String, val type: TokenType)
}