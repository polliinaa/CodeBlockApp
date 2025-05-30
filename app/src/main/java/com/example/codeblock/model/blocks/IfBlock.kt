package com.example.codeblock.model.blocks

import com.example.codeblock.model.ConditionOp
import com.example.codeblock.model.ExecutionContext
import com.example.codeblock.model.Expression

class IfBlock(
    val condition: Condition,
    val thenBlocks: List<Block>,
    val elseBlocks: List<Block> = emptyList()
) : Block {

    fun copy(
        condition: Condition = this.condition,
        thenBlocks: List<Block> = this.thenBlocks,
        elseBlocks: List<Block> = this.elseBlocks
    ) = IfBlock(condition, thenBlocks, elseBlocks)

    override fun execute(context: ExecutionContext) {
        val result = condition.evaluate(context)
        val blocksToExecute = if (result) thenBlocks else elseBlocks
        blocksToExecute.forEach { it.execute(context) }
    }

    data class Condition(
        val left: Expression,
        val op: ConditionOp,
        val right: Expression
    ) {
        fun evaluate(context: ExecutionContext): Boolean {
            val l = left.evaluate(context)
            val r = right.evaluate(context)
            return when (op) {
                ConditionOp.EQ -> l == r
                ConditionOp.NEQ -> l != r
                ConditionOp.GT -> l > r
                ConditionOp.LT -> l < r
                ConditionOp.GTE -> l >= r
                ConditionOp.LTE -> l <= r
            }
        }
    }
}