package com.example.codeblockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.codeblockapp.ui.theme.CodeBlockAppTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.codeblockapp.navigation.SetUpNavGraph
import com.example.codeblockapp.screens.blocks.IFBlockPreview
import com.example.codeblockapp.screens.blocks.NumberBlockPreview


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeBlockAppTheme {

                navController = rememberNavController()
                SetUpNavGraph(navController = navController)
                //PuzzleBlock()

                //DeclareVariableBlockPreview()
                //AssignVariableBlockPreview(listOfVariables = Variables)

                //IFBlockPreview()

                //NumberBlockPreview()
                //MainScreen()
                //ScreenMakeName()
                //CreateAlgorithmScreen("New algorithm")
            }
        }
    }
}



















