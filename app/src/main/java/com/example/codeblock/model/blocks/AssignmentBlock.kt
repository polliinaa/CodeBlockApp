package com.example.codeblock.model.blocks

import com.example.codeblock.model.ExecutionContext
import com.example.codeblock.model.ExecutionException
import com.example.codeblock.model.ExpressionParser

class AssignmentBlock(
    val variable: String,
    val expression: String
) : Block {
    fun copy(variable: String = this.variable, expression: String = this.expression) =
        AssignmentBlock(variable, expression)

    override fun execute(context: ExecutionContext) {
        try {
            val value = ExpressionParser.parse(expression).evaluate(context)
            context.setVariable(variable, value)
        } catch (e: Exception) {
            throw ExecutionException("Ошибка присваивания: ${e.message}")
        }
    }
}