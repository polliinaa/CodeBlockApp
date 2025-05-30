package com.example.codeblock

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.interpreter.Interpreter
import com.example.codeblock.model.HistoryManager
import com.example.codeblock.model.blocks.Block
import com.example.codeblock.model.blocks.blockedits.NestedBlocksEditor

@Composable
fun BlockEditorScreen() {
    val history = remember { HistoryManager<List<Block>>(emptyList()) }
    var blocks by remember { mutableStateOf(emptyList<Block>()) }
    var output by remember { mutableStateOf(emptyList<String>()) }
    val interpreter = remember { Interpreter() }

    fun handleUpdate(newBlocks: List<Block>) {
        history.push(newBlocks)
        blocks = newBlocks
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { history.undo()?.let { blocks = it } },
                enabled = history.canUndo()
            ) {
                Text("Отменить")
            }
            Button(
                onClick = { history.redo()?.let { blocks = it } },
                enabled = history.canRedo()
            ) {
                Text("Вернуть")
            }

            Button(
                onClick = { output = interpreter.interpret(blocks) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Выполнить")
                Text("Выполнить")
            }

        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            NestedBlocksEditor(
                blocks = blocks,
                onUpdate = ::handleUpdate
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (output.any { it.startsWith("Ошибка") }) {
                    Color(0xFFFFEBEE)
                } else {
                    Color(0xFFE8F5E9)
                }
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (output.isEmpty()) {
                    Text(
                        "Результат",
                        color = Color.Gray
                    )
                } else {
                    output.forEach { line ->
                        Text(
                            text = line,
                            color = if (line.startsWith("Ошибка")) Color.Red else Color.Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}