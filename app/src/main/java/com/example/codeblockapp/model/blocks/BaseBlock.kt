package com.example.codeblockapp.model.blocks

import java.util.UUID

enum class BlockType {
    VARIABLE_DECLARATION,
    VARIABLE_ASSIGNMENT,
    PRINT,
    START,
    FINISH,
    IF,

}


abstract class BaseBlock {
    abstract val id: String
    abstract val type: BlockType
    abstract var x: Float
    abstract var y: Float
}

data class DeclareVariableBlockData(
    override val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var value: String = "",
    override var x: Float = 0f,
    override var y: Float = 0f
) : BaseBlock() {
    override val type = BlockType.VARIABLE_DECLARATION
}

//
//data class AssignVariableBlockData(
//    override val id: String = UUID.randomUUID().toString(),
//    var selectedVariable: String = "",
//    var newValue: String = "",
//    override var x: Float = 0f,
//    override var y: Float = 0f
//) : BaseBlock() {
//    override val type = BlockType.VARIABLE_ASSIGNMENT
//}
data class AssignVariableBlockData(
    override val id: String = UUID.randomUUID().toString(),
    var selectedVariable: String = "",
    var newValue: String = "",
    override var x: Float = 0f,
    override var y: Float = 0f
) : BaseBlock() {
    override val type = BlockType.VARIABLE_ASSIGNMENT

    // Функция для безопасного копирования с сохранением значений
    fun safeCopy(
        x: Float = this.x,
        y: Float = this.y,
        selectedVariable: String = this.selectedVariable,
        newValue: String = this.newValue
    ) = this.copy(
        x = x,
        y = y,
        selectedVariable = selectedVariable,
        newValue = newValue
    )
}
class FunctionPrintBlockData(override val id: String = UUID.randomUUID().toString()) : BaseBlock() {
    var value = ""
    override val type = BlockType.PRINT
    override var x: Float = 0f
    override var y: Float = 0f

}

class ConditionIFBlockData(override val id: String = UUID.randomUUID().toString()) : BaseBlock() {
    var valueLeft = ""
    var valueRight = ""
    val condition = ""
    override val type = BlockType.IF
    override var x: Float = 0f
    override var y: Float = 0f
}

class StartBlockData(override val id: String = UUID.randomUUID().toString()) : BaseBlock() {
    override val type = BlockType.START
    override var x: Float = 0f
    override var y: Float = 0f
}

class FinishBlockData(override val id: String = UUID.randomUUID().toString()) : BaseBlock() {
    override val type = BlockType.FINISH
    override var x: Float = 0f
    override var y: Float = 0f
}
