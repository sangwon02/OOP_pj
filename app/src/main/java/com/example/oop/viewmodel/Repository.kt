package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.oop.data.Category
import com.example.oop.data.Routine
import com.example.oop.data.RoutineCategory
import com.example.oop.data.Task

class Repository {
    private val dummyCategories = listOf(
        Category("오전", listOf(
            Task("세수하기", "오전"),
            Task("양치하기", "오전")
        )),
        Category("오후", listOf(
            Task("운동하기", "오후"),
            Task("공부하기", "오후"),
            Task("친구만나기", "오후")
        )),
        Category("야간", listOf(
            Task("저녁하기", "야간"),
            Task("설거지하기", "야간"),
            Task("설거지하기", "야간"),
            Task("설거지하기", "야간"),
            Task("설거지하기", "야간"),
            Task("청소하기", "야간")

        ))
    )

    private val dummyRoutines = listOf(
        RoutineCategory(
            Category("오전", emptyList()),
            listOf(Routine("세수하기"), Routine("양치하기"))
        ),
        RoutineCategory(
            Category("오후", emptyList()),
            listOf(Routine("운동하기"), Routine("공부하기"), Routine("친구만나기"))
        ),
        RoutineCategory(
            Category("야간", emptyList()),
            listOf(Routine("저녁하기"), Routine("설거지하기"))
        )
    )

    private val _categories = MutableLiveData<List<Category>>(dummyCategories)
    private val _routineCategories = MutableLiveData<List<RoutineCategory>>(dummyRoutines)

    fun getCategories(): LiveData<List<Category>> {
        return _categories
    }

    fun getRoutineCategories(): LiveData<List<RoutineCategory>> {
        return _routineCategories
    }
    
}
