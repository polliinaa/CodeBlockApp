package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.blocks.WhileBlock

@Composable
fun WhileBlockEditor(
    block: WhileBlock,
    onUpdate: (WhileBlock) -> Unit
) {
    var showBody by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            IfConditionEditor(
                condition = block.condition,
                onUpdate = { newCondition ->
                    onUpdate(block.copy(condition = newCondition))
                }
            )

            ExpandableSection(
                expanded = showBody,
                onExpandChange = { showBody = it },
                title = "Тело цикла",
                content = {
                    NestedBlocksEditor(
                        blocks = block.body,
                        onUpdate = { newBlocks ->
                            onUpdate(block.copy(body = newBlocks))
                        }
                    )
                }
            )
        }
    }
}