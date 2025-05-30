package com.example.codeblock.model.blocks

import com.example.codeblock.model.ExecutionContext

interface Block {
    fun execute(context: ExecutionContext)
}
