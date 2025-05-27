package com.example.codeblockapp.screens.blocks

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
import com.example.codeblockapp.model.Variables
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun IFBlockPreview() {
    var xOffsetMainIFBlock by remember { mutableStateOf(0f) }
    var yOffsetMainIFBlock by remember { mutableStateOf(0f) }

    var xOffsetStartIFBlock by remember { mutableStateOf(0f) }
    var yOffsetStartIFBlock by remember { mutableStateOf(0f) }

    var xOffsetEndIFBlock by remember { mutableStateOf(0f) }
    var yOffsetEndIFBlock by remember { mutableStateOf(0f) }

    Box(modifier = Modifier
        .offset { IntOffset(xOffsetMainIFBlock.roundToInt(), yOffsetMainIFBlock.roundToInt()) }
        //.padding(30.dp)
        //.fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { _, distance ->
                xOffsetMainIFBlock += distance.x
                yOffsetMainIFBlock += distance.y
            }
        }
    ) {
        IFBlockUI()
    }

    Box(modifier = Modifier
        .offset { IntOffset(xOffsetStartIFBlock.roundToInt(), yOffsetStartIFBlock.roundToInt()) }
        //.padding(30.dp)
        //.fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { _, distance ->
                xOffsetStartIFBlock += distance.x
                yOffsetStartIFBlock += distance.y
            }
        }
    ) {
        IFStartBlock()
    }

    Box(modifier = Modifier
        .offset { IntOffset(xOffsetEndIFBlock.roundToInt(), yOffsetEndIFBlock.roundToInt()) }
        //.padding(30.dp)
        //.fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { _, distance ->
                xOffsetEndIFBlock += distance.x
                yOffsetEndIFBlock += distance.y
            }
        }
    ) {
        IFEndBlock()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IFBlockUI() {

    var valueLeft by remember { mutableStateOf("") }
    var valueRight by remember { mutableStateOf("") }

    val conditionsOperations = mutableListOf(">", "<", "==", "!=", ">=", "<=")

    var isExpandedOfConditionOperation by remember { mutableStateOf(false) }
    var selectedConditionOperation by remember { mutableStateOf(conditionsOperations[0]) }

    PuzzleBlockForVariables(color_bg = colorResource(R.color.butter_yellow),
        color_style = colorResource(R.color.grape_juice),
    )
    {

        Column {
            Box(
                modifier = Modifier
                    //.background(Color.Blue)
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .padding(end = 15.dp),
                contentAlignment = Alignment.CenterEnd,
            )
            {
                Text(
                    text = stringResource(R.string.if_block),
                    fontFamily = Tektur,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = colorResource(R.color.grape_juice)
                )
            }

            Row(
                modifier = Modifier
                    //.background(Color.Green)
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
                    //.background(Color.Magenta)
                    //.padding(start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                BasicTextField(
                    value = valueLeft,
                    onValueChange = { valueLeft = it },
                    modifier = Modifier
                        //.padding(start = 10.dp)
                        //.height(28.dp)
                        .heightIn(min = 28.dp, max = 50.dp)
                        .width(109.dp)
                        //.wrapContentWidth()
                        .wrapContentHeight()
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
//                        .border(
//                            width = 1.dp,
//                            color = colorResource(R.color.lilacs),
//                            shape = RoundedCornerShape(1.dp)
//                        )
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

                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                ){
                    ExposedDropdownMenuBox(
                        expanded = isExpandedOfConditionOperation,
                        onExpandedChange = {isExpandedOfConditionOperation = !isExpandedOfConditionOperation},
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
                                .padding(start = 1.dp, bottom = 5.dp, top = 5.dp, end = 5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(20.dp)
                            ) {
                                BasicTextField(
                                    value = selectedConditionOperation,
                                    onValueChange = {},
                                    readOnly = true,
                                    textStyle = TextStyle(
                                        fontFamily = Arimo,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.butter_yellow),
                                        lineHeight = 12.sp,
                                        textAlign = TextAlign.End
                                    ),
                                    modifier = Modifier
                                        .width(35.dp)
                                        .height(15.dp)
                                        .align(Alignment.CenterVertically)
                                        .padding(end = 8.dp)

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
                            onDismissRequest = {isExpandedOfConditionOperation = false},
                        ) {
                            conditionsOperations.forEachIndexed{ index, conditionOperation ->
                                DropdownMenuItem(
                                    text = { Text(text = conditionOperation)},
                                    onClick = {
                                        selectedConditionOperation = conditionsOperations[index]
                                        isExpandedOfConditionOperation = false },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }

                    }
                }

                BasicTextField(
                    value = valueRight,
                    onValueChange = { valueRight = it },
                    modifier = Modifier
                        //.padding(start = 10.dp)
                        //.height(28.dp)
                        .heightIn(min = 28.dp, max = 50.dp)
                        .width(109.dp)
                        //.wrapContentWidth()
                        .wrapContentHeight()
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
//                        .border(
//                            width = 1.dp,
//                            color = colorResource(R.color.lilacs),
//                            shape = RoundedCornerShape(1.dp)
//                        )
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    textStyle = TextStyle(
                        fontFamily = Arimo,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = colorResource(R.color.grape_juice),
                        lineHeight = 12.sp
                    ),
                    maxLines = 3,

                )

            }

        }
    }
}

@Composable
fun IFStartBlock() {
    PuzzleBlockForStartEnd(
        color_bg = colorResource(R.color.blood_orange),
    ) {
        Text(text = stringResource(R.string.start_block),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorResource(R.color.butter_yellow),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(306.dp)


        )
    }
}

@Composable
fun IFEndBlock() {
    PuzzleBlockForStartEnd(
        color_bg = colorResource(R.color.blood_orange),
    ) {
        Text(text = stringResource(R.string.finish_block),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorResource(R.color.butter_yellow),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(306.dp)


        )
    }
}



