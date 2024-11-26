package com.example.oop.data

data class Routine(
    val id: Int = 0,
    val name: String = "",
    val tasks: List<Task> = listOf() // 관련된 할 일 목록
) {
    // 인자가 없는 기본 생성자
    constructor() : this(0, "", listOf())
}
