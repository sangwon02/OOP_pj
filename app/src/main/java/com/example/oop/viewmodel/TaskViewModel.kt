package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.example.oop.repository.TaskRepository

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

    fun addTaskToCategory(categoryId: String, task: Task) {
        repository.addTaskToCategory(categoryId, task)
    }

    fun deleteTask(categoryId: String, taskId: String) {
        repository.deleteTask(categoryId, taskId)
    }

    fun addCategory(category: Category) {
        repository.addCategory(category)
    }
}
