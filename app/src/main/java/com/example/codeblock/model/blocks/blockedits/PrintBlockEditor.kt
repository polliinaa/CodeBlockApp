package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.blocks.PrintBlock

@Composable
fun PrintBlockEditor(
    block: PrintBlock,
    onUpdate: (PrintBlock) -> Unit
) {
    var expression by remember { mutableStateOf(block.expression) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Print",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Вывод", style = MaterialTheme.typography.titleSmall)
            }

            OutlinedTextField(
                value = expression,
                onValueChange = {
                    expression = it
                    onUpdate(block.copy(expression = it))
                },
                label = { Text("Ввод для вывода") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}