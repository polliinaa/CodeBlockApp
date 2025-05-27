package com.example.codeblockapp.screens.blocks

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.codeblockapp.model.Variables
import com.example.codeblockapp.model.blocks.AssignVariableBlockData

import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@Composable
fun AssignVariableBlockPreview(
    block: AssignVariableBlockData,
    variables: List<String>,
    onUpdate: (AssignVariableBlockData) -> Unit
) {
    // сохраняем текущие значения в состоянии
    val currentState by remember(block.id) {
        mutableStateOf(block)
    }

    Box(modifier = Modifier
        .offset { IntOffset(block.x.roundToInt(), block.y.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                onUpdate(
                    currentState.safeCopy(
                        x = block.x + dragAmount.x,
                        y = block.y + dragAmount.y,
                        selectedVariable = currentState.selectedVariable,
                        newValue = currentState.newValue
                    )
                )
            }
        }
    ) {
        AssignVariableBlockUI(
            block = currentState,
            variables = variables,
            onUpdate = { updatedBlock ->
                // обновляем текущее состояние
                onUpdate(updatedBlock.safeCopy(x = block.x, y = block.y))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignVariableBlockUI(
    block: AssignVariableBlockData,
    variables: List<String>,
    onUpdate: (AssignVariableBlockData) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isExpanded by remember { mutableStateOf(false) }
    var currentBlock by remember { mutableStateOf(block) }

    // обновляем currentBlock при изменении входного блока
    LaunchedEffect(block) {
        currentBlock = block
    }

    Box(modifier = Modifier
        .offset { IntOffset(currentBlock.x.roundToInt(), currentBlock.y.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                currentBlock = currentBlock.copy(
                    x = currentBlock.x + dragAmount.x,
                    y = currentBlock.y + dragAmount.y
                )
                onUpdate(currentBlock)
            }
        }
    ) {
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
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.44f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.name_variable),
                        fontFamily = Arimo,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = colorResource(R.color.grape_juice)
                    )

                    Box(modifier = Modifier.padding(start = 10.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = { isExpanded = it }
                        ) {
                            Box(
                                modifier = Modifier
                                    .menuAnchor()
                                    .background(
                                        color = colorResource(R.color.grape_juice),
                                        shape = RoundedCornerShape(7.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(20.dp)
                                ) {
                                    BasicTextField(
                                        value = currentBlock.selectedVariable.ifEmpty {
                                            stringResource(R.string.select_variable)
                                        },
                                        onValueChange = {},
                                        readOnly = true,
                                        textStyle = TextStyle(
                                            fontFamily = Arimo,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp,
                                            color = colorResource(R.color.lilacs),
                                            lineHeight = 12.sp
                                        ),
                                        modifier = Modifier
                                            .widthIn(min = 50.dp)
                                            .wrapContentWidth()
                                    )

                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = null,
                                        tint = colorResource(R.color.lilacs)
                                    )
                                }
                            }

                            ExposedDropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false }
                            ) {
                                variables.forEach { variableName ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                variableName,
                                                color = colorResource(R.color.grape_juice)
                                            )
                                        },
                                        onClick = {
                                            currentBlock = currentBlock.copy(
                                                selectedVariable = variableName
                                            )
                                            onUpdate(currentBlock)
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
                        .fillMaxHeight(0.7f),
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
                        value = currentBlock.newValue,
                        onValueChange = { newValue ->
                            currentBlock = currentBlock.copy(newValue = newValue)
                            onUpdate(currentBlock)
                        },
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .height(28.dp)
                            .widthIn(min = 50.dp)
                            .wrapContentWidth()
                            .background(
                                color = colorResource(R.color.light_great),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 7.dp, vertical = 6.dp),
                        textStyle = TextStyle(
                            fontFamily = Arimo,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = colorResource(R.color.grape_juice),
                            lineHeight = 12.sp
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                    )
                }
            }
        }
    }
}