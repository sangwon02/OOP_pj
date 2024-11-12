package com.example.oop.viewmodel

import com.example.oop.data.Category
import com.example.oop.data.Task

class Repository {
    private val dummyCategories = listOf(
        Category("카테고리1", listOf(Task("할 일1-1"), Task("할 일1-2"), Task("할 일1-3"))),
        Category("카테고리2", listOf(Task("할 일2-1"), Task("할 일2-2"))),
        Category("카테고리3", listOf(Task("할 일3-1"), Task("할 일3-2")))
    )

    fun getCategories(): List<Category> {
        return dummyCategories
    }
}
