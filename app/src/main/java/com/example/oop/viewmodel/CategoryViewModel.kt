package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Category
import com.example.oop.repository.TaskRepository

// 카테고리 관련 데이터를 관리
class CategoryViewModel : ViewModel() {
    private val repository = TaskRepository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        fetchCategories()
    }
    // 레포지토리의 getCategories를 호출하여 데이터를 LiveData에 업데이트.
    private fun fetchCategories() {
        repository.getCategories { categories ->
            _categories.value = categories
        }
    }
    // 레포지토리의 addCategory 호출해 카테고리 추가
    fun addCategory(category: Category) {
        repository.addCategory(category)
    }
}
