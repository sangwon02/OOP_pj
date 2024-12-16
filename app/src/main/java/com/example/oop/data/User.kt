package com.example.oop.data

data class User(
    var username: String = "",
    var category: MutableMap<String, Category> = mutableMapOf(),
    val id: String = ""
)
