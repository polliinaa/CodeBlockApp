package com.example.codeblockapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur

@Preview
@Composable
fun ScreenMakeName(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_yellow)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        ButtonGoBack()
        TextCreateTheName()
        InputNameOfAlgorithm()
        ButtonGoNext()
        PrintIcon()
    }

}

//КНОПКА СЛЕВА СВЕРХУ "НАЗАД"
@Composable
fun ButtonGoBack(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.10f)
            .padding(start = 10.dp),

        contentAlignment = Alignment.BottomStart,
    ){
       OutlinedButton (
           onClick = {
            //навигация на главный экран
            },

           colors = ButtonDefaults.outlinedButtonColors(
               contentColor = colorResource(R.color.great)
           ),

           border = BorderStroke(1.dp, colorResource(R.color.great)),

           shape = RoundedCornerShape(12.dp),
            )
       {
             Text(text = stringResource(R.string.go_back),
                 fontFamily = Arimo,
                 fontWeight = FontWeight.Normal,
                 fontSize = 16.sp,


             )
        }
    }
}


//ТЕКСТ "ПРИДУМАЙТЕ НАЗВАНИЕ АЛГОРИТМУ"
@Composable
fun TextCreateTheName() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .background(color = Color.White)
            .padding(top = 100.dp, bottom = 30.dp),

        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(R.string.create_the_name_for_new_algorithm),
            style = TextStyle(
                lineHeight = 35.sp
            ),
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = colorResource(R.color.dark_purple),
            textAlign = TextAlign.Center,
            )
    }
}

@Composable
fun InputNameOfAlgorithm(){

    Box () {

        var name by remember { mutableStateOf("") }
        OutlinedTextField(name,
            onValueChange = { name = it },

            modifier = Modifier
                .fillMaxWidth(0.8f)
//                .background(Color.Blue)
                .padding(top = 30.dp, bottom = 20.dp),


            textStyle = TextStyle(
                fontFamily = Arimo,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = colorResource(R.color.dark_purple)
            ),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,

                focusedIndicatorColor = colorResource(R.color.light_purple),
                unfocusedIndicatorColor = colorResource(R.color.light_purple),

                cursorColor = Color.DarkGray,
            )
            ,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

            placeholder = { Text(text= "New algorithm",
                fontFamily = Arimo,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = colorResource(R.color.light_purple)
            )},
        )
    }
}

//КНОПКА "ПРОДОЛЖИТЬ"
@Composable
fun ButtonGoNext(){
    Box(
        modifier = Modifier
            .padding(top = 10.dp),
    ) {
        Button(onClick = {/*/переход к экрану создания*/},
            modifier = Modifier
                .width(166.dp)
                .height(53.dp),

            shape = RoundedCornerShape(22.dp),

            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 10.dp,
            ),

            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = colorResource(R.color.light_purple),
            ),
        )
        {
            Text(text = stringResource(R.string.go_next),
                fontFamily = Arimo,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
    }
}

//КАРТИНКА УТКА
@Composable
fun PrintIcon() {
    Box() {
        Image(painter = painterResource(R.drawable.icon_duck),
            contentDescription = "Duck",
            modifier = Modifier
                .padding(top = 120.dp, end = 80.dp)
                .size(216.dp)


            )
    }
}
