package com.example.oop.data

data class User(
    var username: String = "",
    var isFriend: String = "",
    var progression: Int = 0,
    val usercode: Int = 0
) {
    // 인자가 없는 기본 생성자
    constructor() : this("")
}
