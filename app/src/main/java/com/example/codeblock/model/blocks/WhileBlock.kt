package com.example.codeblock.model.blocks

import com.example.codeblock.model.ExecutionContext

class WhileBlock(
    val condition: IfBlock.Condition,
    val body: List<Block>
) : Block {
    override fun execute(context: ExecutionContext) {
        while (condition.evaluate(context)) {
            body.forEach { it.execute(context) }
        }
    }

    fun copy(
        condition: IfBlock.Condition = this.condition,
        body: List<Block> = this.body
    ) = WhileBlock(condition, body)
}