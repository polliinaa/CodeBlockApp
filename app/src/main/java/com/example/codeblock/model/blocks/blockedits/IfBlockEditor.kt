package com.example.codeblock.model.blocks.blockedits

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.blocks.IfBlock

@Composable
fun IfBlockEditor(
    block: IfBlock,
    onUpdate: (IfBlock) -> Unit
) {
    var showThen by remember { mutableStateOf(false) }
    var showElse by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            IfConditionEditor(
                condition = block.condition,
                onUpdate = { newCondition ->
                    onUpdate(block.copy(condition = newCondition))
                }
            )
            ExpandableSection(
                expanded = showThen,
                onExpandChange = { showThen = it },
                title = "Eсли условие выполнено",
                content = {
                    NestedBlocksEditor(
                        blocks = block.thenBlocks,
                        onUpdate = { newBlocks ->
                            onUpdate(block.copy(thenBlocks = newBlocks))
                        }
                    )
                }
            )
            ExpandableSection(
                expanded = showElse,
                onExpandChange = { showElse = it },
                title = "Eсли условие не выполнено",
                content = {
                    NestedBlocksEditor(
                        blocks = block.elseBlocks,
                        onUpdate = { newBlocks ->
                            onUpdate(block.copy(elseBlocks = newBlocks))
                        }
                    )
                }
            )
        }
    }
}

@Composable
internal fun ExpandableSection(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandChange(!expanded) }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                contentDescription = if (expanded) "Свернуть" else "Развернуть",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        }

        AnimatedVisibility(visible = expanded) {
            Box(modifier = Modifier.padding(start = 16.dp)) {
                content()
            }
        }
    }
}