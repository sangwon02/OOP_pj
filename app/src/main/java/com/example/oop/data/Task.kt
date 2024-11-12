package com.example.oop.data

data class Task(
    val name: String,
    val category: String,
    val isChecked: Boolean = false // 기본값으로 false 설정
)