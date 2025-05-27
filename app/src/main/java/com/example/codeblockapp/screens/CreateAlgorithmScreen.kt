package com.example.codeblockapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.codeblockapp.R
import com.example.codeblockapp.model.blocks.AssignVariableBlockData
import com.example.codeblockapp.model.blocks.BaseBlock
import com.example.codeblockapp.model.blocks.BlockType
import com.example.codeblockapp.model.blocks.ConditionIFBlockData
import com.example.codeblockapp.model.blocks.DeclareVariableBlockData
import com.example.codeblockapp.model.blocks.FinishBlockData
import com.example.codeblockapp.model.blocks.FunctionPrintBlockData
import com.example.codeblockapp.model.blocks.StartBlockData
import com.example.codeblockapp.screens.blocks.AssignVariableBlockPreview
import com.example.codeblockapp.screens.blocks.AssignVariableBlockUI
import com.example.codeblockapp.screens.blocks.DeclareVariableBlockPreview
import com.example.codeblockapp.screens.blocks.DeclareVariableBlockUI
import com.example.codeblockapp.screens.blocks.FinishAlgorithmBlockPreview
import com.example.codeblockapp.screens.blocks.FunctionPrintBlockPreview
import com.example.codeblockapp.screens.blocks.FunctionPrintBlockUI
import com.example.codeblockapp.screens.blocks.IFBlockPreview
import com.example.codeblockapp.screens.blocks.IFBlockUI
import com.example.codeblockapp.screens.blocks.StartAlgorithmBlockPreview
import com.example.codeblockapp.ui.theme.Arimo
import com.example.codeblockapp.ui.theme.Tektur

//ГЛАВНАЯ ФУНКЦИЯ ЭКРАНА
@Composable
fun CreateAlgorithmScreen(name: String, navController: NavController) {

    val blocks = remember { mutableStateListOf<BaseBlock>() }
    val variables = remember { mutableStateMapOf<String, Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.light_yellow))
    ) {
        //ВЕРХНЯЯ ЧАСТЬ (название алгоритма и кнопка "Сохранить"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
                //.background(Color.Blue),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(0.53f)
                .padding(start = 15.dp, end = 7.dp)
            ) {
                UniqueNameAlgorithm(name)
            }
            Box (contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(start = 15.dp, end = 7.dp, top = 10.dp)
            ){
                ButtonStartAlgorithm()
            }
            Box (contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(start = 1.dp, end = 1.dp, top = 10.dp)
            ){
                ButtonSaveAlgorithm(navController)
            }
        }

        Row(
            modifier = Modifier
                .background(colorResource(R.color.very_light_green))
                .height(128.dp)
        ) {
            Column {
                Text(text = stringResource(R.string.add_block),
                    fontFamily = Arimo,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = colorResource(R.color.great),
                    modifier = Modifier
                        .padding(start = 15.dp, top = 8.dp)
                    )

                BlockToolbar { clicedButton ->
                    //логика появления кнопки
                    when(clicedButton.type){
                        BlockType.VARIABLE_DECLARATION -> {blocks.add(DeclareVariableBlockData()) }
                        BlockType.VARIABLE_ASSIGNMENT -> {blocks.add(AssignVariableBlockData())}
                        BlockType.PRINT -> {blocks.add(FunctionPrintBlockData())}
                        BlockType.IF -> {blocks.add(ConditionIFBlockData())}
                        BlockType.START -> {blocks.add(StartBlockData())}
                        BlockType.FINISH -> {blocks.add(FinishBlockData())}

                    }
                    println("Нажата кнопка: ${clicedButton.label}")
                }
            }

        }

        //ОБЛАСТЬ С БЛОКАМИ



        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            blocks.forEachIndexed { index, block ->
                when (block) {
                    is DeclareVariableBlockData -> {
                        DeclareVariableBlockPreview(
                            block = block,
                            variables = variables,
                            onUpdate = { updatedBlock ->
                                blocks[index] = updatedBlock
                            }
                        )
                    }
                    is AssignVariableBlockData -> {
                        AssignVariableBlockPreview (
                            block = block,
                            variables = variables.keys.toList(),
                            onUpdate = { updatedBlock ->
                                blocks[index] = updatedBlock
                            }
                        )
                    }
                    is FunctionPrintBlockData -> FunctionPrintBlockPreview()
                    is ConditionIFBlockData -> IFBlockPreview()
                    is StartBlockData -> StartAlgorithmBlockPreview()
                    is FinishBlockData -> FinishAlgorithmBlockPreview()

                    // В будущем другие типы
                }
            }
        }

    }



}



//шаблон для упрощенных кнопок из верхнего меню
data class BlockSimpleButtonUi(
    val label: String,
    val bgColor: Color,
    val textColor: Color,
    val type: BlockType
)

//для отображения одной кнопки
@Composable
fun BlockSimpleTypeButton(button: BlockSimpleButtonUi, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(144.dp)
            .height(56.dp)
        ,

        shape = RoundedCornerShape(6.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),

        colors = ButtonDefaults.buttonColors(
            contentColor = button.textColor,
            containerColor = button.bgColor,
        ),

        ) {
        Text(text = button.label,
            fontFamily = Tektur,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,

        )

    }
}


//LAZYROW С КНОПКАМИ ДЛЯ ВЫБОРА НОВОГО БЛОКА
@Composable
fun BlockToolbar(onButtonClick: (BlockSimpleButtonUi) -> Unit) {
    //список кнопок
    val blockSimpleButtons = listOf(
        BlockSimpleButtonUi(
            label = stringResource(R.string.declare_a_variable),
            bgColor = colorResource(R.color.grape_juice),
            textColor = colorResource(R.color.lilacs),
            type = BlockType.VARIABLE_DECLARATION
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.assign_a_variable),
            bgColor = colorResource(R.color.lilacs),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.VARIABLE_ASSIGNMENT
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.print_block),
            bgColor = colorResource(R.color.iced_blue),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.PRINT
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.if_block),
            bgColor = colorResource(R.color.butter_yellow),
            textColor = colorResource(R.color.grape_juice),
            type = BlockType.IF
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.start_block),
            bgColor = colorResource(R.color.tea_green),
            textColor = colorResource(R.color.forest),
            type = BlockType.START
        ),
        BlockSimpleButtonUi(
            label = stringResource(R.string.finish_block),
            bgColor = colorResource(R.color.tea_green),
            textColor = colorResource(R.color.forest),
            type = BlockType.FINISH
        ),

    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(99.dp)
            .padding(start = 5.dp, end = 5.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        items(blockSimpleButtons) {button ->
            BlockSimpleTypeButton(button = button) {
                onButtonClick(button)
            }
        }
    }
}


//ИМЯ АЛГОРИТМА
@Composable
fun UniqueNameAlgorithm(name: String){
    Text(text = name,
        fontFamily = Tektur,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = colorResource(R.color.dark_purple),

    )
}

//КНОПКА СОХРАНИТЬ
@Composable
fun ButtonSaveAlgorithm(navController: NavController) {
    Button(onClick = {},

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.light_purple),
            contentColor = Color.White
        ),

        shape = RoundedCornerShape(8.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),

        ) {

        Text(text = stringResource(R.string.to_save),
            fontFamily = Arimo,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
    }
}

//КНОПКА ЗАПУСТИТЬ
@Composable
fun ButtonStartAlgorithm() {
    Button(onClick = {/*экран запуска алгоритма*/},

        modifier = Modifier
            .width(52.dp)
            .height(52.dp)
        ,

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange),
            contentColor = colorResource(R.color.light_orange)
        ),

        shape = RoundedCornerShape(26.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),
    ) {
        Text(text = stringResource(R.string.start_algorithm),
            fontFamily = Tektur,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 3.dp)
        )
    }

}



//КНОПКА ДОБАВИТЬ БЛОК
@Composable
fun ButtonAddBlock() {
    Button(onClick = {/*открывается окно со списком блоков*/},

        modifier = Modifier
            .width(382.dp)
            .height(42.dp)
        ,

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.light_green),
            contentColor = colorResource(R.color.light_yellow)
        ),

        shape = RoundedCornerShape(6.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
        ),
    ) {
        Text(text = stringResource(R.string.add_block),
            fontFamily = Tektur,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
        )
    }
}
