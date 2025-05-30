package com.example.codeblock.interpreter

import com.example.codeblock.model.*
import com.example.codeblock.model.blocks.Block
import com.example.codeblock.model.blocks.IfBlock
import com.example.codeblock.model.blocks.PrintBlock
import com.example.codeblock.model.blocks.WhileBlock

class Interpreter {
    fun interpret(blocks: List<Block>): List<String> {
        val output = mutableListOf<String>()
        val context = ExecutionContext()
        var hasPrintBlocks = false

        try {
            hasPrintBlocks = blocks.any { it is PrintBlock } ||
                    blocks.any {
                        it is IfBlock && (it.thenBlocks.hasPrintBlocks() || it.elseBlocks.hasPrintBlocks())
                    } ||
                    blocks.any { it is WhileBlock && it.body.hasPrintBlocks() }

            if (!hasPrintBlocks) {
                return listOf("Ошибка: в алгоритме отсутствуют блоки Print")
            }

            // Выполняем код
            blocks.forEach { block ->
                when (block) {
                    is PrintBlock -> {
                        val value = ExpressionParser.parse(block.expression).evaluate(context)
                        output.add("${block.expression} = $value")
                    }
                    else -> block.execute(context)
                }
            }
        } catch (e: Exception) {
            return listOf("Ошибка выполнения: ${e.message}")
        }

        return if (output.isEmpty()) {
            listOf("")
        } else {
            output
        }
    }
}
private fun List<Block>.hasPrintBlocks(): Boolean {
    return this.any { it is PrintBlock } ||
            this.any {
                it is IfBlock && (it.thenBlocks.hasPrintBlocks() || it.elseBlocks.hasPrintBlocks())
            } ||
            this.any { it is WhileBlock && it.body.hasPrintBlocks() }
}