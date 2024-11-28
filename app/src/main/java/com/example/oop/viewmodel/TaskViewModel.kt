package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.example.oop.repository.TaskRepository

// 할 일 관련 데이터를 관리.
class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        repository.getCategories { categories ->
            _categories.value = categories
        }
    }

    // 특정 카테고리에 할 일을 추가
    fun addTaskToCategory(categoryId: String, task: Task) {
        repository.addTaskToCategory(categoryId, task)
    }

    // 할 일 상태 업데이트
    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        repository.updateTaskStatus(categoryId, taskId, isChecked)
    }
}
