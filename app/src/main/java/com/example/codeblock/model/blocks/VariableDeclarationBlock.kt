package com.example.codeblock.model.blocks

import com.example.codeblock.model.ExecutionContext

class VariableDeclarationBlock(
    val declarations: Map<String, Int?>
) : Block {
    fun copy(declarations: Map<String, Int?> = this.declarations) =
        VariableDeclarationBlock(declarations)

    override fun execute(context: ExecutionContext) {
        declarations.forEach { (name, value) ->
            context.declareVariable(name, value ?: 0)
        }
    }
}