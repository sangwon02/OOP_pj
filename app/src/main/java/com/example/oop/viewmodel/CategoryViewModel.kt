package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Category
import com.example.oop.repository.TaskRepository

// 카테고리 관련 데이터를 관리하는 ViewModel 클래스
class CategoryViewModel : ViewModel() {
    private val repository = TaskRepository() // TaskRepository 인스턴스 - 데이터베이스 연동
    private val _categories = MutableLiveData<List<Category>>() // 카테고리 목록 LiveData - 옵저빙 가능한 카테고리 목록
    val categories: LiveData<List<Category>> get() = _categories // 외부에서 접근 가능한 카테고리 목록 LiveData

    init {
        fetchCategories() // 초기 카테고리 목록 가져오기 - ViewModel 생성 시 카테고리 목록 초기화
    }

    // 레포지토리의 getCategories를 호출하여 데이터를 LiveData에 업데이트.
    private fun fetchCategories() {
        repository.getCategories { categories ->
            _categories.value = categories // LiveData에 카테고리 목록 설정 - 가져온 카테고리 목록을 LiveData에 설정
        }
    }

    // 레포지토리의 addCategory 호출해 카테고리 추가
    fun addCategory(category: Category) {
        repository.addCategory(category) // Repository를 통해 카테고리 추가 - 레포지토리에 카테고리 추가 요청
    }
}