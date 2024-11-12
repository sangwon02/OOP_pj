package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop.data.Category
import com.example.oop.data.Routine
import com.example.oop.data.RoutineCategory
import com.example.oop.data.Task

class Repository {
    private val dummyCategories = listOf(
        Category("카테고리 1", listOf(
            Task("할 일 1-1", "카테고리 1"),
            Task("할 일 1-2", "카테고리 1")
        )),
        Category("카테고리 2", listOf(
            Task("할 일 2-1", "카테고리 2"),
            Task("할 일 2-2", "카테고리 2")
        )),
        Category("카테고리 3", listOf(
            Task("할 일 3-1", "카테고리 3"),
            Task("할 일 3-2", "카테고리 3")
        ))
    )

    private val dummyRoutines = listOf(
        RoutineCategory(Category("루틴 카테고리 1", emptyList()), listOf(Routine("루틴 1"), Routine("루틴 2"))),
        RoutineCategory(Category("루틴 카테고리 2", emptyList()), listOf(Routine("루틴 3"), Routine("루틴 4"))),
        RoutineCategory(Category("루틴 카테고리 3", emptyList()), listOf(Routine("루틴 5"), Routine("루틴 6")))
    )

    private val _categories = MutableLiveData<List<Category>>(dummyCategories)
    private val _routineCategories = MutableLiveData<List<RoutineCategory>>(dummyRoutines)

    fun getCategories(): LiveData<List<Category>> {
        return _categories
    }

    fun getRoutineCategories(): LiveData<List<RoutineCategory>> {
        return _routineCategories
    }

    fun getTasks(): LiveData<List<Task>> {
        val tasks = dummyCategories.flatMap { it.tasks }
        return MutableLiveData(tasks)
    }
}
