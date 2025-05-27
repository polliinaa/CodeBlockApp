package com.example.codeblockapp.screens.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.R
import com.example.codeblockapp.ui.theme.Tektur

@Preview(showBackground = true)
@Composable
fun NumberBlockPreview() {
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { focusManager.clearFocus()}

    ,
        contentAlignment = Alignment.Center
        )

    {
        NumberBlock()
    }

}


@Composable
fun NumberBlock() {
    var valueNumber by remember { mutableStateOf("0") }

    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        color = colorResource(R.color.blood_orange),
        fontFamily = Tektur,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        lineHeight = 12.sp
    )

    val textWidth = with(LocalDensity.current) {
        val result = textMeasurer.measure(
            text = AnnotatedString(valueNumber),
            style = textStyle
        )
        result.size.width.toDp()
    }

    val minWidth = 24.dp
    val horizontalPadding = 4.dp
    val boxWidth = (textWidth + horizontalPadding).coerceAtLeast(minWidth)

    Box(
        modifier = Modifier
            .height(28.dp)
            .width(boxWidth)
            .background(
                color = colorResource(R.color.butter_yellow),
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = valueNumber,
            onValueChange = { newValue -> valueNumber = newValue.take(20) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 5.dp),
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
    }
}

