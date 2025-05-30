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

data class AssignVariableBlockData(
    override val id: String = UUID.randomUUID().toString(),
    var selectedVariable: String = "",
    var newValue: String = "",
    override var x: Float = 0f,
    override var y: Float = 0f
) : BaseBlock() {
    override val type = BlockType.VARIABLE_ASSIGNMENT

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
data class FunctionPrintBlockData(
    override val id: String = UUID.randomUUID().toString(),
    var expression: String = "",
    override var x: Float = 0f,
    override var y: Float = 0f
) : BaseBlock() {
    override val type = BlockType.PRINT
}



data class ConditionIFBlockData(
    override val id: String = UUID.randomUUID().toString(),
    var valueLeft: String = "",
    var valueRight: String = "",
    var selectedConditionOperation: String = "==",
    var startBlockId: String? = null,
    var finishBlockId: String? = null,
    override var x: Float = 0f,
    override var y: Float = 0f
) : BaseBlock() {
    override val type = BlockType.IF

    fun safeCopy(
        x: Float = this.x,
        y: Float = this.y,
        valueLeft: String = this.valueLeft,
        valueRight: String = this.valueRight,
        selectedConditionOperation: String = this.selectedConditionOperation,
        startBlockId: String? = this.startBlockId,
        finishBlockId: String? = this.finishBlockId
    ) = copy(
        x = x,
        y = y,
        valueLeft = valueLeft,
        valueRight = valueRight,
        selectedConditionOperation = selectedConditionOperation,
        startBlockId = startBlockId,
        finishBlockId = finishBlockId
    )
}

data class StartBlockData(
    override val id: String = UUID.randomUUID().toString(),
    override var x: Float = 0f,
    override var y: Float = 0f,
    var parentIfBlockId: String? = null
) : BaseBlock() {
    override val type = BlockType.START
}

data class FinishBlockData(
    override val id: String = UUID.randomUUID().toString(),
    override var x: Float = 0f,
    override var y: Float = 0f,
    var parentIfBlockId: String? = null
) : BaseBlock() {
    override val type = BlockType.FINISH
}

