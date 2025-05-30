package com.example.codeblock.model.blocks.blockedits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.codeblock.model.ConditionOp
import com.example.codeblock.model.Expression
import com.example.codeblock.model.blocks.IfBlock

@Composable
fun IfConditionEditor(
    condition: IfBlock.Condition,
    onUpdate: (IfBlock.Condition) -> Unit
) {
    var leftVariable by remember { mutableStateOf(extractVariableName(condition.left)) }
    var rightValue by remember { mutableStateOf(extractNumberValue(condition.right)) }
    var currentOp by remember { mutableStateOf(condition.op) }

    val operatorSymbols = remember {
        mapOf(
            ConditionOp.EQ to "==",
            ConditionOp.NEQ to "!=",
            ConditionOp.GT to ">",
            ConditionOp.LT to "<",
            ConditionOp.GTE to ">=",
            ConditionOp.LTE to "<="
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Поле для левой части (переменная)
        OutlinedTextField(
            value = leftVariable,
            onValueChange = { newValue ->
                leftVariable = newValue
                updateCondition(leftVariable, rightValue, currentOp, onUpdate)
            },
            label = { Text("Переменная") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OperatorDropdown(
            currentOp = currentOp,
            operatorSymbols = operatorSymbols,
            onOperatorSelected = { newOp ->
                currentOp = newOp
                updateCondition(leftVariable, rightValue, currentOp, onUpdate)
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = rightValue,
            onValueChange = { newValue ->
                rightValue = newValue.filter { it.isDigit() || it == '-' }
                updateCondition(leftVariable, rightValue, currentOp, onUpdate)
            },
            label = { Text("Значение") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun OperatorDropdown(
    currentOp: ConditionOp,
    operatorSymbols: Map<ConditionOp, String>,
    onOperatorSelected: (ConditionOp) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(80.dp)) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(operatorSymbols[currentOp] ?: "==")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            operatorSymbols.forEach { (op, symbol) ->
                DropdownMenuItem(
                    text = { Text(symbol) },
                    onClick = {
                        onOperatorSelected(op)
                        expanded = false
                    }
                )
            }
        }
    }
}

private fun extractVariableName(expr: Expression): String {
    return when (expr) {
        is Expression.Variable -> expr.name
        else -> ""
    }
}

private fun extractNumberValue(expr: Expression): String {
    return when (expr) {
        is Expression.Number -> expr.value.toString()
        else -> "0"
    }
}

private fun updateCondition(
    leftText: String,
    rightText: String,
    op: ConditionOp,
    onUpdate: (IfBlock.Condition) -> Unit
) {
    val leftExpr = if (leftText.isNotEmpty()) {
        Expression.Variable(leftText)
    } else {
        Expression.Variable("var")
    }

    val rightValue = rightText.toIntOrNull() ?: 0
    val rightExpr = Expression.Number(rightValue)

    onUpdate(IfBlock.Condition(leftExpr, op, rightExpr))
}