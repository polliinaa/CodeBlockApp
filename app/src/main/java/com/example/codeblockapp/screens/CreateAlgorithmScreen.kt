package com.example.codeblockapp.screens

import ExpressionEvaluator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.codeblockapp.R
import com.example.codeblockapp.model.blocks.AssignVariableBlockData
import com.example.codeblockapp.model.blocks.BaseBlock
import com.example.codeblockapp.model.blocks.BlockType
import com.example.codeblockapp.model.blocks.ConditionIFBlockData
import com.example.codeblockapp.model.blocks.DeclareVariableBlockData
import com.example.codeblockapp.model.blocks.FinishBlockData
import com.example.codeblockapp.model.blocks.FunctionPrintBlockData
import com.example.codeblockapp.model.blocks.StartBlockData
import com.example.codeblockapp.screens.blocks.AssignVariableBlockPreview
import com.example.codeblockapp.screens.blocks.AssignVariableBlockUI
import com.example.codeblockapp.screens.blocks.DeclareVariableBlockPreview
import com.example.codeblockapp.screens.blocks.DeclareVariableBlockUI
import com.example.codeblockapp.screens.blocks.FinishAlgorithmBlockPreview
import com.example.codeblockapp.screens.blocks.FunctionPrintBlockPreview
import com.example.codeblockapp.screens.blocks.FunctionPrintBlockUI
import com.example.codeblockapp.screens.blocks.IFBlockPreview
import com.example.codeblockapp.screens.blocks.IFBlockUI
import com.example.codeblockapp.screens.blocks.StartAlgorithmBlockPreview
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import java.util.Stack

//ГЛАВНАЯ ФУНКЦИЯ ЭКРАНА
@Composable
fun CreateAlgorithmScreen(name: String, navController: NavController) {
    val blocks = remember { mutableStateListOf<BaseBlock>() }
    val variables = remember { mutableStateMapOf<String, Int>() }
    val printOutput = remember { mutableStateListOf<String>() }
    var showOutputDialog by remember { mutableStateOf(false) }
    val evaluator = remember {
        ExpressionEvaluator(
            initialVariables = variables,
            forbiddenVariables = setOf()
        )
    }

    fun executeProgram() {
        printOutput.clear()
        val blockStack = Stack<Triple<Boolean, String?, ExpressionEvaluator>>()
        var currentCondition = true
        var currentIfBlockId: String? = null
        var evaluator = ExpressionEvaluator(mutableMapOf())

        val sortedBlocks = blocks.sortedBy { it.y }

        val ifBlockRanges = mutableMapOf<String, Triple<Int, Int, Boolean>>() // start, end, isConditionMet
        
        sortedBlocks.forEachIndexed { index, block ->
            if (block is ConditionIFBlockData) {
                val startIndex = sortedBlocks.indexOfFirst { 
                    it is StartBlockData && it.parentIfBlockId == block.id 
                }
                val endIndex = sortedBlocks.indexOfFirst { 
                    it is FinishBlockData && it.parentIfBlockId == block.id 
                }
                if (startIndex != -1 && endIndex != -1) {
                    // Вычисляем условие IF
                    val conditionResult = try {
                        evaluator.evaluateCondition(
                            block.valueLeft,
                            block.selectedConditionOperation,
                            block.valueRight
                        )
                    } catch (e: Exception) {
                        printOutput.add("IF condition error: ${e.message}")
                        false
                    }
                    ifBlockRanges[block.id] = Triple(startIndex, endIndex, conditionResult)
                }
            }
        }

        evaluator = ExpressionEvaluator(mutableMapOf())

        sortedBlocks.forEachIndexed { index, block ->
            try {
                val parentIf = ifBlockRanges.entries.find { (_, range) ->
                    index > range.first && index < range.second
                }

                if (parentIf != null && !parentIf.value.third) {
                    return@forEachIndexed
                }

                when (block) {
                    is DeclareVariableBlockData -> {
                        if (parentIf != null) {
                            block.name.split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                                .forEach { varName ->
                                    try {
                                        val value = block.value.toIntOrNull() ?: 0
                                        evaluator.currentContext()[varName] = value
                                    } catch (e: Exception) {
                                        printOutput.add("Error declaring '$varName': ${e.message}")
                                    }
                                }
                        } else {
                            block.name.split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                                .forEach { varName ->
                                    try {
                                        val value = block.value.toIntOrNull() ?: 0
                                        evaluator.getGlobalContext()[varName] = value
                                    } catch (e: Exception) {
                                        printOutput.add("Error declaring '$varName': ${e.message}")
                                    }
                                }
                        }
                    }

                    is AssignVariableBlockData -> {
                        try {
                            if (block.selectedVariable.isEmpty()) return@forEachIndexed

                            val result = evaluator.evaluate(block.newValue)
                            
                            if (parentIf != null) {
                                if (evaluator.currentContext().containsKey(block.selectedVariable)) {
                                    evaluator.currentContext()[block.selectedVariable] = result
                                } else if (evaluator.getGlobalContext().containsKey(block.selectedVariable)) {
                                    evaluator.getGlobalContext()[block.selectedVariable] = result
                                } else {
                                    printOutput.add("Error: Variable '${block.selectedVariable}' not found in current scope")
                                }
                            } else {
                                if (evaluator.getGlobalContext().containsKey(block.selectedVariable)) {
                                    evaluator.getGlobalContext()[block.selectedVariable] = result
                                } else {
                                    printOutput.add("Error: Variable '${block.selectedVariable}' not found in global scope")
                                }
                            }
                        } catch (e: Exception) {
                            printOutput.add("Error assigning variable: ${e.message}")
                        }
                    }

                    is ConditionIFBlockData -> {
                        evaluator.pushContext()
                    }

                    is StartBlockData -> {
                    }

                    is FinishBlockData -> {
                        if (parentIf != null) {
                            evaluator.popContext()
                        }
                    }

                    is FunctionPrintBlockData -> {
                        try {
                            if (block.expression.isBlank()) {
                                printOutput.add("Print: Empty expression")
                            } else {
                                val variableName = block.expression.trim()
                                if (!evaluator.hasVariable(variableName)) {
                                    printOutput.add("Error: Variable '$variableName' not found in current scope")
                                    return@forEachIndexed
                                }
                                val result = evaluator.evaluate(block.expression)
                                printOutput.add(result.toString())
                            }
                        } catch (e: Exception) {
                            printOutput.add("Print error: ${e.message ?: "Invalid expression"}")
                        }
                    }
                }
            } catch (e: Exception) {
                printOutput.add("Execution error: ${e.message}")
            }
        }
        
        showOutputDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_yellow))
    ) {
        // ВЕРХНЯЯ ЧАСТЬ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.53f)
                    .padding(start = 15.dp, end = 7.dp)
            ) {
                UniqueNameAlgorithm(name)
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(start = 15.dp, end = 7.dp, top = 10.dp)
            ) {
                ButtonStartAlgorithm(onClick = {
                    executeProgram()
                })
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(start = 1.dp, end = 1.dp, top = 10.dp)
            ) {
                ButtonSaveAlgorithm(navController)
            }
        }

        Row(
            modifier = Modifier
                .background(colorResource(R.color.very_light_green))
                .height(128.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.add_block),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = colorResource(R.color.great),
                    modifier = Modifier.padding(start = 15.dp, top = 8.dp)
                )

                BlockToolbar { clicedButton ->
                    when (clicedButton.type) {
                        BlockType.VARIABLE_DECLARATION -> blocks.add(DeclareVariableBlockData())
                        BlockType.VARIABLE_ASSIGNMENT -> blocks.add(AssignVariableBlockData())
                        BlockType.PRINT -> blocks.add(FunctionPrintBlockData())
                        BlockType.IF -> {
                            val ifBlock = ConditionIFBlockData()
                            val startBlock = StartBlockData(parentIfBlockId = ifBlock.id)
                            val finishBlock = FinishBlockData(parentIfBlockId = ifBlock.id)

                            ifBlock.startBlockId = startBlock.id
                            ifBlock.finishBlockId = finishBlock.id

                            blocks.add(ifBlock)
                            blocks.add(startBlock)
                            blocks.add(finishBlock)
                        }
                        BlockType.START -> {}
                        BlockType.FINISH -> {}
                    }
                    println("Нажата кнопка: ${clicedButton.label}")
                }
            }
        }

        // ОБЛАСТЬ С БЛОКАМИ
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp)
            ) {
                blocks.forEach { block ->
                    key(block.id) {
                        when (block) {
                            is DeclareVariableBlockData -> DeclareVariableBlockPreview(
                                block = block,
                                variables = variables,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                }
                            )

                            is AssignVariableBlockData -> AssignVariableBlockPreview(
                                block = block,
                                variables = variables,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                },
                                onVariableUpdate = { name, value ->
                                    variables[name] = value
                                }
                            )

                            is FunctionPrintBlockData -> FunctionPrintBlockPreview(
                                block = block,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                }
                            )

                            is ConditionIFBlockData -> IFBlockPreview(
                                block = block,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                },
                                variables = variables,
                                evaluator = evaluator
                            )
                            is StartBlockData -> StartAlgorithmBlockPreview(
                                block = block,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                }
                            )
                            is FinishBlockData -> FinishAlgorithmBlockPreview(
                                block = block,
                                onUpdate = { updatedBlock ->
                                    val index = blocks.indexOfFirst { it.id == updatedBlock.id }
                                    if (index != -1) blocks[index] = updatedBlock
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(300.dp))
            }
        }

        if (showOutputDialog) {
            PrintOutputDialog(
                output = printOutput,
                onDismiss = { showOutputDialog = false }
            )
        }
    }
}


//шаблон для упрощенных кнопок из верхнего меню
data class BlockSimpleButtonUi(
    val label: String,
    val bgColor: Color,
    val textColor: Color,
    val type: BlockType
)

//для отображения одной кнопки
@Composable
fun BlockSimpleTypeButton(button: BlockSimpleButtonUi, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(144.dp)
            .height(56.dp),

        shape = RoundedCornerShape(6.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),

        colors = ButtonDefaults.buttonColors(
            contentColor = button.textColor,
            containerColor = button.bgColor,
        ),

        ) {
        Text(
            text = button.label,
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,

            )

    }
}


//LAZYROW С КНОПКАМИ ДЛЯ ВЫБОРА НОВОГО БЛОКА
@Composable
fun BlockToolbar(onButtonClick: (BlockSimpleButtonUi) -> Unit) {
    //список кнопок
    val blockSimpleButtons = listOf(
        BlockSimpleButtonUi(
            label = stringResource(R.string.declare_a_variable),
            bgColor = colorResource(R.color.grape_juice),
            textColor = colorResource(R.color.lilacs),
            type = BlockType.VARIABLE_DECLARATION
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.assign_a_variable),
            bgColor = colorResource(R.color.lilacs),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.VARIABLE_ASSIGNMENT
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.print_block),
            bgColor = colorResource(R.color.iced_blue),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.PRINT
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.if_block),
            bgColor = colorResource(R.color.butter_yellow),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.IF
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.start_block),
            bgColor = colorResource(R.color.tea_green),
            textColor = colorResource(R.color.forest),
            type = BlockType.START
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.finish_block),
            bgColor = colorResource(R.color.tea_green),
            textColor = colorResource(R.color.forest),
            type = BlockType.FINISH
        ),

        )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(99.dp)
            .padding(start = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        items(blockSimpleButtons) { button ->
            BlockSimpleTypeButton(button = button) {
                onButtonClick(button)
            }
        }
    }
}


//ИМЯ АЛГОРИТМА
@Composable
fun UniqueNameAlgorithm(name: String) {
    Text(
        text = name,
        fontFamily = Tektur,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = colorResource(R.color.dark_purple),

        )
}

//КНОПКА СОХРАНИТЬ
@Composable
fun ButtonSaveAlgorithm(navController: NavController) {
    Button(
        onClick = {},

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.light_purple),
            contentColor = Color.White
        ),

        shape = RoundedCornerShape(8.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),

        ) {

        Text(
            text = stringResource(R.string.to_save),
            fontFamily = Arimo,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
    }
}

//КНОПКА ЗАПУСТИТЬ
@Composable
fun ButtonStartAlgorithm(onClick: () -> Unit) {
    Button(
        onClick = onClick,

        modifier = Modifier
            .width(52.dp)
            .height(52.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange),
            contentColor = colorResource(R.color.light_orange)
        ),

        shape = RoundedCornerShape(26.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),
    ) {
        Text(
            text = stringResource(R.string.start_algorithm),
            fontFamily = Tektur,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 3.dp)
        )
    }

}



@Composable
fun PrintOutputDialog(
    output: List<String>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .width(350.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        containerColor = colorResource(R.color.light_yellow),
        titleContentColor = colorResource(R.color.dark_purple),
        textContentColor = colorResource(R.color.grape_juice),
        title = {
            Text(
                text = stringResource(R.string.program_output),
                fontFamily = Tektur,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .heightIn(max = 400.dp)
            ) {
                if (output.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_output),
                        fontFamily = Arimo,
                        color = colorResource(R.color.grape_juice),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    output.forEachIndexed { index, line ->
                        Text(
                            text = "$line",
                            fontFamily = Arimo,
                            color = colorResource(R.color.grape_juice),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        if (index < output.size - 1) {
                            Divider(
                                color = colorResource(R.color.lilacs),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.grape_juice),
                        contentColor = colorResource(R.color.lilacs)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.ok),
                        fontFamily = Tektur,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}
