package com.example.codeblockapp.screens.blocks

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.R
import com.example.codeblockapp.ui.theme.Tektur
import kotlin.math.roundToInt
@Preview
@Composable
fun StartAlgorithmBlockPreview() {
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
        StartAlgorithmBlockUI()

    }
}

@Composable
fun StartAlgorithmBlockUI() {
    PuzzleBlockForStartAlgorithm (
        color_bg = colorResource(R.color.tea_green),
    ) {
        Text(text = stringResource(R.string.start_block_program),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = colorResource(R.color.forest),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(306.dp)
        )
    }
}