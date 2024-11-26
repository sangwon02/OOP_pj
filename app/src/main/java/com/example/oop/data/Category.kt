package com.example.oop.data

data class Category(
    val id: String = "",
    val name: String = "",
    val tasks: MutableMap<String, Task> = mutableMapOf()
)
