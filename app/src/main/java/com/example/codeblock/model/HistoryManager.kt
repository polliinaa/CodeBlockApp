package com.example.codeblock.model

class HistoryManager<T>(initialState: T) {
    private val stack = mutableListOf(initialState)
    private var pointer = 0

    fun push(state: T) {
        stack.subList(pointer + 1, stack.size).clear()
        stack.add(state)
        pointer = stack.size - 1
    }

    fun undo(): T? = if (canUndo()) stack[--pointer] else null
    fun redo(): T? = if (canRedo()) stack[++pointer] else null
    fun current(): T = stack[pointer]
    fun canUndo(): Boolean = pointer > 0
    fun canRedo(): Boolean = pointer < stack.size - 1
}