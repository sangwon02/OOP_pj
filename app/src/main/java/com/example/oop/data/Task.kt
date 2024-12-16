package com.example.oop.data

data class Task(
    val id: String = "",
    val name: String = "",
    val createdAt: String = "",
    var checked: Boolean = false
)