package com.example.codeblockapp.screens.blocks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblockapp.R

@Preview(showBackground = true)
@Composable
fun PuzzleBlockForStartAlgorithmUI() {
    PuzzleBlockForStartAlgorithm (
        color_bg = colorResource(R.color.grape_juice),
        //modifier = Modifier
//            .width(120.dp)
//            .height(150.dp)
        //.padding(20.dp)
        //.fillMaxSize(),

    )
    {

    }
}



@Composable
fun PuzzleBlockForStartAlgorithm(
    color_bg: Color,
    modifier: Modifier = Modifier
        .size(width = 307.dp, height = 38.dp),
    content: @Composable BoxScope.() -> Unit,

    ) {
    Canvas(modifier = modifier)
    {
        val path = Path().apply {
            moveTo(8.dp.toPx(), 0.dp.toPx())
            quadraticTo(x1 = 1.dp.toPx(), y1 = 2.dp.toPx(),
                x2 = 0.dp.toPx(), y2 = 8.dp.toPx())
            lineTo(0.dp.toPx(), 19.dp.toPx())
            quadraticTo(x1 = 1.dp.toPx(), y1 = 25.dp.toPx(),
                x2 = 8.dp.toPx(), y2 = 27.dp.toPx())
            lineTo(20.dp.toPx(), 27.dp.toPx())
            lineTo(45.dp.toPx(), 36.dp.toPx())
            quadraticTo(x1 = 46.dp.toPx(), y1 = 37.dp.toPx(),
                x2 = 52.dp.toPx(), y2 = 36.dp.toPx())
            lineTo(78.dp.toPx(), 27.dp.toPx())
            lineTo(299.dp.toPx(), 27.dp.toPx())
            quadraticTo(x1 = 306.dp.toPx(), y1 = 25.dp.toPx(),
                x2 = 307.dp.toPx(), y2 = 19.dp.toPx())
            lineTo(307.dp.toPx(), 8.dp.toPx())
            quadraticTo(x1 = 306.dp.toPx(), y1 = 1.dp.toPx(),
                x2 = 300.dp.toPx(), y2 = 0.dp.toPx())
            lineTo(20.dp.toPx(), 0.dp.toPx())
            close()
        }

        drawPath(path = path, color = color_bg)


    }

    Box(modifier = modifier
        ,
        content = content
    )

}





