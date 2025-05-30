package com.example.codeblockapp.screens.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.codeblockapp.R
import com.example.codeblockapp.model.blocks.DeclareVariableBlockData
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@Composable
fun DeclareVariableBlockPreview(
    block: DeclareVariableBlockData,
    variables: MutableMap<String, Int>,
    onUpdate: (DeclareVariableBlockData) -> Unit
) {
    var xOffset by remember { mutableStateOf(block.x) }
    var yOffset by remember { mutableStateOf(block.y) }
    
    var name by remember(block.id) { mutableStateOf(block.name) }
    var value by remember(block.id) { mutableStateOf(block.value) }

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
                        name = name,
                        value = value
                    ))
                }
            }
    ) {
        DeclareVariableBlockUI(
            block = block.copy(
                name = name,
                value = value
            ),
            variables = variables,
            onUpdate = { updatedBlock ->
                name = updatedBlock.name
                value = updatedBlock.value
                onUpdate(updatedBlock.copy(x = xOffset, y = yOffset))
            }
        )
    }
}

@Composable
fun DeclareVariableBlockUI(
    block: DeclareVariableBlockData,
    variables: MutableMap<String, Int>,
    onUpdate: (DeclareVariableBlockData) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    PuzzleBlockForVariables(
        color_bg = colorResource(R.color.grape_juice),
        color_style = colorResource(R.color.lilacs)
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
                    text = stringResource(R.string.declare_a_variable),
                    fontFamily = Tektur,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = colorResource(R.color.lilacs)
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
                    color = colorResource(R.color.lilacs)
                )

                BasicTextField(
                    value = block.name,
                    onValueChange = { newName ->
                        block.name.split(",")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                            .forEach { variables.remove(it) }

                        onUpdate(block.copy(
                            name = newName,
                            value = block.value,
                            x = block.x,
                            y = block.y
                        ))

                        newName.split(",")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                            .forEach { variables[it] = block.value.toIntOrNull() ?: 0 }
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(28.dp)
                        .widthIn(min = 50.dp)
                        .wrapContentWidth()
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.lilacs),
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
                    singleLine = true
                )
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
                    color = colorResource(R.color.lilacs)
                )

                BasicTextField(
                    value = block.value,
                    onValueChange = { newValue ->
                        onUpdate(block.copy(
                            value = newValue,
                            name = block.name,
                            x = block.x,
                            y = block.y
                        ))

                        block.name.split(",")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                            .forEach { variables[it] = newValue.toIntOrNull() ?: 0 }
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(28.dp)
                        .widthIn(min = 50.dp)
                        .wrapContentWidth()
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.lilacs),
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
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            block.name.split(",")
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }
                                .forEach { variables[it] = block.value.toIntOrNull() ?: 0 }
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}