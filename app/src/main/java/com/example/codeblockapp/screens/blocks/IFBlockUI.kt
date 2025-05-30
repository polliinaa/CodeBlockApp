package com.example.codeblockapp.screens.blocks

import ExpressionEvaluator
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.R
import com.example.codeblockapp.model.blocks.ConditionIFBlockData
import com.example.codeblockapp.model.blocks.FinishBlockData
import com.example.codeblockapp.model.blocks.StartBlockData
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IFBlockPreview(
    block: ConditionIFBlockData,
    onUpdate: (ConditionIFBlockData) -> Unit,
    variables: Map<String, Int>,
    evaluator: ExpressionEvaluator
) {
    var xOffset by remember { mutableStateOf(block.x) }
    var yOffset by remember { mutableStateOf(block.y) }
    
    var valueLeft by remember(block.id) { mutableStateOf(block.valueLeft) }
    var valueRight by remember(block.id) { mutableStateOf(block.valueRight) }
    var selectedOperation by remember(block.id) { mutableStateOf(block.selectedConditionOperation) }

    Box(modifier = Modifier
        .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                xOffset += dragAmount.x
                yOffset += dragAmount.y
                onUpdate(block.safeCopy(
                    x = xOffset,
                    y = yOffset,
                    valueLeft = valueLeft,
                    valueRight = valueRight,
                    selectedConditionOperation = selectedOperation
                ))
            }
        }
    ) {
        IFBlockUI(
            block = block.copy(
                valueLeft = valueLeft,
                valueRight = valueRight,
                selectedConditionOperation = selectedOperation
            ),
            onUpdate = { updatedBlock ->
                valueLeft = updatedBlock.valueLeft
                valueRight = updatedBlock.valueRight
                selectedOperation = updatedBlock.selectedConditionOperation
                onUpdate(updatedBlock.safeCopy(x = xOffset, y = yOffset))
            },
            variables = variables,
            evaluator = evaluator
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IFBlockUI(
    block: ConditionIFBlockData,
    onUpdate: (ConditionIFBlockData) -> Unit,
    variables: Map<String, Int>,
    evaluator: ExpressionEvaluator
) {
    val conditionsOperations = listOf(">", "<", "==", "!=", ">=", "<=")
    var isExpandedOfConditionOperation by remember { mutableStateOf(false) }

    PuzzleBlockForVariables(
        color_bg = colorResource(R.color.butter_yellow),
        color_style = colorResource(R.color.grape_juice),
    ) {
        Column {
            // Заголовок IF
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .padding(end = 15.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = stringResource(R.string.if_block),
                    fontFamily = Tektur,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = colorResource(R.color.grape_juice)
                )
            }

            // Условие
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.condition),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = colorResource(R.color.grape_juice)
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                // левое выражение
                BasicTextField(
                    value = block.valueLeft,
                    onValueChange = {
                        onUpdate(block.safeCopy(valueLeft = it))
                    },
                    modifier = Modifier
                        .heightIn(min = 28.dp, max = 50.dp)
                        .width(109.dp)
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    textStyle = TextStyle(
                        fontFamily = Arimo,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = colorResource(R.color.grape_juice),
                        lineHeight = 12.sp
                    ),
                    maxLines = 3
                )

                // оператор сравнения
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = isExpandedOfConditionOperation,
                        onExpandedChange = { isExpandedOfConditionOperation = !isExpandedOfConditionOperation },
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.blood_orange),
                                shape = RoundedCornerShape(7.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .menuAnchor()
                                .background(
                                    color = colorResource(R.color.blood_orange),
                                    shape = RoundedCornerShape(7.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.width(50.dp).height(20.dp)
                            ) {
                                Text(
                                    text = block.selectedConditionOperation,
                                    fontFamily = Arimo,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = colorResource(R.color.butter_yellow),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    tint = colorResource(R.color.butter_yellow)
                                )
                            }
                        }

                        ExposedDropdownMenu(
                            expanded = isExpandedOfConditionOperation,
                            onDismissRequest = { isExpandedOfConditionOperation = false },
                        ) {
                            conditionsOperations.forEach { conditionOperation ->
                                DropdownMenuItem(
                                    text = { Text(text = conditionOperation) },
                                    onClick = {
                                        onUpdate(block.safeCopy(selectedConditionOperation = conditionOperation))
                                        isExpandedOfConditionOperation = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }

                // правое выражение
                BasicTextField(
                    value = block.valueRight,
                    onValueChange = {
                        onUpdate(block.safeCopy(valueRight = it))
                    },
                    modifier = Modifier
                        .heightIn(min = 28.dp, max = 50.dp)
                        .width(109.dp)
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    textStyle = TextStyle(
                        fontFamily = Arimo,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = colorResource(R.color.grape_juice),
                        lineHeight = 12.sp
                    ),
                    maxLines = 3
                )
            }
        }
    }
}
@Composable
fun StartAlgorithmBlockPreview(
    block: StartBlockData,
    onUpdate: (StartBlockData) -> Unit
) {
    var xOffset by remember { mutableStateOf(block.x) }
    var yOffset by remember { mutableStateOf(block.y) }

    Box(
        modifier = Modifier
            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    xOffset += dragAmount.x
                    yOffset += dragAmount.y
                    onUpdate(block.copy(
                        x = xOffset,
                        y = yOffset,
                        parentIfBlockId = block.parentIfBlockId
                    ))
                }
            }
    ) {
    PuzzleBlockForStartEnd(
        color_bg = colorResource(R.color.blood_orange),
    ) {
        Text(
                text = stringResource(R.string.start_block),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorResource(R.color.butter_yellow),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(306.dp)
        )
        }
    }
}

@Composable
fun FinishAlgorithmBlockPreview(
    block: FinishBlockData,
    onUpdate: (FinishBlockData) -> Unit
) {
    var xOffset by remember { mutableStateOf(block.x) }
    var yOffset by remember { mutableStateOf(block.y) }

    Box(
        modifier = Modifier
            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    xOffset += dragAmount.x
                    yOffset += dragAmount.y
                    onUpdate(block.copy(
                        x = xOffset,
                        y = yOffset,
                        parentIfBlockId = block.parentIfBlockId
                    ))
                }
            }
    ) {
    PuzzleBlockForStartEnd(
        color_bg = colorResource(R.color.blood_orange),
    ) {
        Text(
                text = stringResource(R.string.finish_block),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorResource(R.color.butter_yellow),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(306.dp)
        )
        }
    }
}



