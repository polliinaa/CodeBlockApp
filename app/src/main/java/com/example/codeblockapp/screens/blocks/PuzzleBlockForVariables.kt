package com.example.codeblockapp.screens.blocks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codeblockapp.R

@Preview(showBackground = true)
@Composable
fun PuzzleBlockForVariablesUI() {
    PuzzleBlockForVariables(
        color_bg = colorResource(R.color.grape_juice),
        color_style = colorResource(R.color.lilacs),
        //modifier = Modifier
//            .width(120.dp)
//            .height(150.dp)
            //.padding(20.dp)
            //.fillMaxSize(),

    ) {
    }
}


@Composable
fun PuzzleBlockForVariables(
    color_bg: Color,
    color_style : Color,
    modifier: Modifier = Modifier
        .size(width = 307.dp, height = 144.dp),
    content: @Composable BoxScope.() -> Unit,

) {
    Canvas(modifier = modifier)
    {
         val path = Path().apply {
            moveTo(8.dp.toPx(), 0.dp.toPx())
            quadraticTo(x1 = 1.dp.toPx(), y1 = 2.dp.toPx(),
                        x2 = 0.dp.toPx(), y2 = 8.dp.toPx())
            lineTo(0.dp.toPx(), 125.dp.toPx())
            quadraticTo(x1 = 1.dp.toPx(), y1 = 132.dp.toPx(),
                        x2 = 8.dp.toPx(), y2 = 133.dp.toPx())
            lineTo(20.dp.toPx(), 133.dp.toPx())
            lineTo(45.dp.toPx(), 142.dp.toPx())
            quadraticTo(x1 = 46.dp.toPx(), y1 = 143.dp.toPx(),
                x2 = 52.dp.toPx(), y2 = 142.dp.toPx())
            lineTo(78.dp.toPx(), 133.dp.toPx())
            lineTo(299.dp.toPx(), 133.dp.toPx())
            quadraticTo(x1 = 306.dp.toPx(), y1 = 132.dp.toPx(),
                x2 = 307.dp.toPx(), y2 = 125.dp.toPx())
            lineTo(307.dp.toPx(), 8.dp.toPx())
            quadraticTo(x1 = 306.dp.toPx(), y1 = 1.dp.toPx(),
                x2 = 300.dp.toPx(), y2 = 0.dp.toPx())
            lineTo(78.dp.toPx(), 0.dp.toPx())
            lineTo(53.dp.toPx(), 8.dp.toPx())
            quadraticTo(x1 = 49.dp.toPx(), y1 = 9.dp.toPx(),
                x2 = 44.dp.toPx(), y2 = 8.dp.toPx())
            lineTo(20.dp.toPx(), 0.dp.toPx())
            close()
        }

        drawPath(path = path, color = color_bg)

        drawLine(
            color = color_style,
            start = Offset(0f, 38.dp.toPx()),
            end = Offset(307.dp.toPx(), 38.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
    }

    Box(modifier = modifier
        ,
        content = content
    )

}





