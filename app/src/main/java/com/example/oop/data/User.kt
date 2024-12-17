package com.example.oop.data

data class User(
    var username: String = "",
    var category: MutableMap<String, Category> = mutableMapOf(),
    var progression: Int = 0,
    val id: String = ""
)
