package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.blocks.VariableDeclarationBlock

@Composable
fun VariableBlockEditor(
    block: VariableDeclarationBlock,
    onUpdate: (VariableDeclarationBlock) -> Unit
) {
    var variablesText by remember { mutableStateOf(
        block.declarations.keys.joinToString(", ")
    ) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Объявление", style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = variablesText,
                onValueChange = { text ->
                    variablesText = text
                    val declarations = text.split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .associateWith { null }
                    onUpdate(VariableDeclarationBlock(declarations))
                },
                label = { Text("Имя/на переменной/ых") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}