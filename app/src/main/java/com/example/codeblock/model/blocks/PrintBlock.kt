package com.example.codeblock.model.blocks

import com.example.codeblock.model.ExecutionContext
import com.example.codeblock.model.ExpressionParser

class PrintBlock(
    val expression: String
) : Block {
    override fun execute(context: ExecutionContext) {
        val value = ExpressionParser.parse(expression).evaluate(context)
        println("Print: $value")
    }

    fun copy(expression: String = this.expression) = PrintBlock(expression)
}