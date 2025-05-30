package com.example.codeblock.model

class ExecutionContext(
    internal val variables: MutableMap<String, Int> = mutableMapOf(),
    private val parent: ExecutionContext? = null
) {
    fun declareVariable(name: String, value: Int = 0) {
        if (variables.containsKey(name)) {
            throw RuntimeException("Переменная  '$name' уже объявлена")
        }
        variables[name] = value
    }

    fun setVariable(name: String, value: Int) {
        if (variables.containsKey(name)) {
            variables[name] = value
        } else {
            parent?.setVariable(name, value)
                ?: throw RuntimeException("Переменная  '$name' не найдена")
        }
    }

    fun getVariable(name: String): Int {
        return variables[name] ?: parent?.getVariable(name)
        ?: throw RuntimeException("Переменная  '$name' не найдена")
    }

    fun hasVariable(name: String): Boolean {
        return variables.containsKey(name) || (parent?.hasVariable(name) ?: false)
    }
}