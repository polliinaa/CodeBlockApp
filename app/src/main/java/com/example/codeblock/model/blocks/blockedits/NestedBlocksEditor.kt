package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.ConditionOp
import com.example.codeblock.model.Expression
import com.example.codeblock.model.blocks.AssignmentBlock
import com.example.codeblock.model.blocks.Block
import com.example.codeblock.model.blocks.IfBlock
import com.example.codeblock.model.blocks.PrintBlock
import com.example.codeblock.model.blocks.VariableDeclarationBlock
import com.example.codeblock.model.blocks.WhileBlock

@Composable
fun NestedBlocksEditor(
    blocks: List<Block>,
    onUpdate: (List<Block>) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
    ) {
        if (blocks.isEmpty()) {
            Text("Нет действий", color = Color.Gray, modifier = Modifier.padding(8.dp))
        } else {
            blocks.forEachIndexed { index, block ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = getBlockColor(block))
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { onUpdate(blocks.toMutableList().apply { removeAt(index) }) }
                            ) {
                                Icon(Icons.Default.Delete, "Удалить блок")
                            }
                        }

                        when (block) {
                            is AssignmentBlock -> AssignmentBlockEditor(block) { updated ->
                                onUpdate(blocks.toMutableList().apply { set(index, updated) })
                            }
                            is VariableDeclarationBlock -> VariableBlockEditor(block) { updated ->
                                onUpdate(blocks.toMutableList().apply { set(index, updated) })
                            }
                            is IfBlock -> IfBlockEditor(block) { updated ->
                                onUpdate(blocks.toMutableList().apply { set(index, updated) })
                            }
                            is WhileBlock -> WhileBlockEditor(block) { updated ->
                                onUpdate(blocks.toMutableList().apply { set(index, updated) })
                            }
                            is PrintBlock -> PrintBlockEditor(block) { updated ->
                                onUpdate(blocks.toMutableList().apply { set(index, updated) })
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        AddBlockButton(onAdd = { onUpdate(blocks + it) })
    }
}

private fun getBlockColor(block: Block): Color {
    return when (block) {
        is AssignmentBlock -> Color(0xFFE8F5E9)
        is VariableDeclarationBlock -> Color(0xFFE3F2FD)
        is IfBlock -> Color(0xFFFFF8E1)
        is WhileBlock -> Color(0xFFE1F5FE)
        is PrintBlock -> Color(0xFFF3E5F5)
        else -> Color(0xFFF5F5F5)
    }
}

@Composable
private fun AddBlockButton(onAdd: (Block) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { showMenu = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("+ Добавить блок")
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            listOf(
                "Объявление" to { VariableDeclarationBlock(mapOf("var" to null)) },
                "Присваивание" to { AssignmentBlock("var", "0") },
                "Условие" to { createDefaultIfBlock() },
                "Цикл While" to { createDefaultWhileBlock() },
                "Вывод" to { PrintBlock("0") }
            ).forEach { (name, blockCreator) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onAdd(blockCreator())
                        showMenu = false
                    }
                )
            }
        }
    }
}


private fun createDefaultWhileBlock(): WhileBlock {
    return WhileBlock(
        condition = IfBlock.Condition(
            left = Expression.Variable("x"),
            op = ConditionOp.LT,
            right = Expression.Number(10)
        ),
        body = emptyList()
    )
}

private fun createDefaultIfBlock(): IfBlock {
    return IfBlock(
        condition = IfBlock.Condition(
            left = Expression.Variable("x"),
            op = ConditionOp.EQ,
            right = Expression.Number(0)
        ),
        thenBlocks = emptyList(),
        elseBlocks = emptyList()
    )
}