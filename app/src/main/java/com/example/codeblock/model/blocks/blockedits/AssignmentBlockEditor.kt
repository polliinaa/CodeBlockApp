package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.blocks.AssignmentBlock

@Composable
fun AssignmentBlockEditor(
    block: AssignmentBlock,
    onUpdate: (AssignmentBlock) -> Unit
) {
    var variable by remember { mutableStateOf(block.variable) }
    var expression by remember { mutableStateOf(block.expression) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Присваивание значения", style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = variable,
                onValueChange = {
                    variable = it
                    onUpdate(block.copy(variable = it))
                },
                label = { Text("Имя переменной") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = expression,
                onValueChange = {
                    expression = it
                    onUpdate(block.copy(expression = it))
                },
                label = { Text("Выражение") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}