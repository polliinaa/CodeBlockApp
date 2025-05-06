package com.example.codeblockapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur

//ОТОБРАЖЕНИЕ ВЕРХНЕЙ ЧАСТИ ЭКРАНА(НЕИЗМЕНЯЕМА)
@Composable
fun Head(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f),
        contentAlignment = Alignment.Center

    ){
        Text(text = stringResource(id = R.string.my_algorithms),
            modifier = Modifier,
            color = colorResource(R.color.dark_purple),
            fontSize = 40.sp,
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

//ОТОБРАЖЕНИЕ ТОГО, ЧТО У ПОЛЬЗОВАТЕЛЯ НЕТ СОХРАНЕННЫХ АЛГОРИТМОВ(надпись, кнопка, картинка)
@Composable
fun EmptyStateContent() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)

        ){
            Text(text = stringResource(id = R.string.empty_algorithms),
                color = colorResource(R.color.great),
                fontFamily = Arimo,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
        }

        Box (
            modifier = Modifier
                .padding(bottom = 40.dp)
        ){

            Button(
                onClick = {
                    Log.d("info", "Переход к экрану создания")
                },

                modifier = Modifier
                    .width(296.dp)
                    .height(53.dp)
                ,

                shape = RoundedCornerShape(22.dp),

                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                ),

                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(R.color.light_purple),
                ),

                ) {
                Text(text = stringResource(R.string.button_create_new_algorithm),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
            }
        }

        Box () {
            Image(
                painter = painterResource(id = R.drawable.icon_empty_algorithms),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 50.dp, top = 70.dp, end = 50.dp, bottom = 0.dp)
                    .size(170.dp),
            )
        }
    }
}


//ОТОБРАЖЕНИЕ СПИСКА СОХРАНЕННЫХ АЛГОРИТМОВ(иконка лягушки, кнопка добавить)
@Composable
fun AlgorithmList(algorithms: SnapshotStateList<String>){



}