package com.example.codeblockapp.screens.blocks

import ExpressionEvaluator
import androidx.compose.foundation.background
import androidx.compose.foundation.excludeFromSystemGesture
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.R
import com.example.codeblockapp.model.blocks.AssignVariableBlockData

import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@Composable
fun AssignVariableBlockPreview(
    block: AssignVariableBlockData,
    variables: Map<String, Int>,
    onUpdate: (AssignVariableBlockData) -> Unit,
    onVariableUpdate: (String, Int) -> Unit
) {
    var xOffset by remember { mutableStateOf(block.x) }
    var yOffset by remember { mutableStateOf(block.y) }
    
    // Сохраняем состояние полей ввода
    var selectedVariable by remember(block.id) { mutableStateOf(block.selectedVariable) }
    var newValue by remember(block.id) { mutableStateOf(block.newValue) }

    Box(
        modifier = Modifier
            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    xOffset += dragAmount.x
                    yOffset += dragAmount.y
                    onUpdate(block.safeCopy(
                        x = xOffset,
                        y = yOffset,
                        selectedVariable = selectedVariable,
                        newValue = newValue
                    ))
                }
            }
    ) {
        AssignVariableBlockUI(
            block = block.copy(
                selectedVariable = selectedVariable,
                newValue = newValue
            ),
            variables = variables,
            onUpdate = { updatedBlock ->
                selectedVariable = updatedBlock.selectedVariable
                newValue = updatedBlock.newValue
                onUpdate(updatedBlock.safeCopy(x = xOffset, y = yOffset))
            },
            onVariableUpdate = onVariableUpdate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignVariableBlockUI(
    block: AssignVariableBlockData,
    variables: Map<String, Int>,
    onUpdate: (AssignVariableBlockData) -> Unit,
    onVariableUpdate: (String, Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isExpanded by remember { mutableStateOf(false) }
    val variableNames by remember { derivedStateOf { variables.keys.toList() } }

    PuzzleBlockForVariables(
        color_bg = colorResource(R.color.lilacs),
        color_style = colorResource(R.color.grape_juice)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .padding(end = 15.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(R.string.assign_a_variable),
                    fontFamily = Tektur,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = colorResource(R.color.grape_juice)
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.select_variable),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = colorResource(R.color.grape_juice)
                )

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = !isExpanded }
                    ) {
                        BasicTextField(
                            value = block.selectedVariable,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .width(109.dp)
                                .height(28.dp)
                                .background(
                                    color = colorResource(R.color.light_great),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 7.dp, vertical = 6.dp),
                            textStyle = TextStyle(
                                fontFamily = Arimo,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = colorResource(R.color.grape_juice)
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            variableNames.forEach { name ->
                                DropdownMenuItem(
                                    text = {
                                        Text("$name = ${variables[name]}")
                                    },
                                    onClick = {
                                        onUpdate(block.copy(selectedVariable = name))
                                        isExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.value_of_variable),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = colorResource(R.color.grape_juice)
                )

                BasicTextField(
                    value = block.newValue,
                    onValueChange = { newValue ->
                        onUpdate(block.copy(newValue = newValue))
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(28.dp)
                        .widthIn(min = 50.dp)
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (block.selectedVariable.isNotEmpty() && block.newValue.isNotBlank()) {
                                try {
                                    val evaluator = ExpressionEvaluator().apply {
                                        pushContext(variables)
                                    }
                                    val result = evaluator.evaluate(block.newValue)
                                    onVariableUpdate(block.selectedVariable, result)
                                } catch (e: Exception) {
                                    println("Ошибка вычисления: ${e.message}")
                                }
                            }
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}