package com.example.oop.data

data class User(
    val name: String = ""
) {
    // 인자가 없는 기본 생성자
    constructor() : this("")

    var progress: Double = 0.0

    fun calProgress(): Double {
        return 0.0
    }
}
