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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.R
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt

@Composable
fun FunctionPrintBlockPreview() {
    var xOffset by remember { mutableStateOf(0f) }
    var yOffset by remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
        //.padding(30.dp)
        //.fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { _, distance ->
                xOffset += distance.x
                yOffset += distance.y
            }
        }
    ) {
        FunctionPrintBlockUI()

    }
}

@Composable
fun FunctionPrintBlockUI() {

    var valueOfPrint by remember { mutableStateOf("") }

    PuzzleBlockForPrint (color_bg = colorResource(R.color.iced_blue),
        color_style = colorResource(R.color.grape_juice),
    )
    {

        Column {
            Box(modifier = Modifier
                //.background(Color.Blue)
                .fillMaxWidth()
                .fillMaxHeight(0.37f)
                .padding(end = 15.dp)
                ,
                contentAlignment = Alignment.CenterEnd,
            )
            {
                Text(
                    text = stringResource(R.string.print_block),
                    fontFamily = Tektur,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = colorResource(R.color.grape_juice)
                )
            }

            Row(
                modifier = Modifier
                    //.background(Color.Green)
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                ,
                verticalAlignment = Alignment.CenterVertically,


                ) {
                BasicTextField(
                    value = valueOfPrint,
                    onValueChange = { valueOfPrint = it },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(28.dp)
                        .widthIn(min = 50.dp)
                        .wrapContentWidth()
                        .background(
                            color = colorResource(R.color.light_great),
                            shape = RoundedCornerShape(1.dp)
                        )
//                        .border(
//                            width = 1.dp,
//                            color = colorResource(R.color.grape_juice),
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
                    singleLine = true
                )

            }
        }
    }
}