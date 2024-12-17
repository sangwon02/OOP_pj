package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.example.oop.repository.TaskRepository

// 할 일 관련 데이터를 관리하는 ViewModel 클래스
class TaskViewModel : ViewModel() {
    private val repository = TaskRepository() // TaskRepository 인스턴스 - 데이터베이스 연동
    private val _categories = MutableLiveData<List<Category>>() // 카테고리 목록 LiveData - 옵저빙 가능한 카테고리 목록
    val categories: LiveData<List<Category>> get() = _categories // 외부에서 접근 가능한 카테고리 목록 LiveData

    init {
        fetchCategories() // 초기 카테고리 목록 가져오기 - ViewModel 생성 시 카테고리 목록 초기화
    }

    // 카테고리 목록 가져오기 함수
    private fun fetchCategories() {
        repository.getCategories { categories ->
            _categories.value = categories // LiveData에 카테고리 목록 설정 - 가져온 카테고리 목록을 LiveData에 설정
        }
    }

    // 특정 카테고리에 할 일을 추가
    fun addTaskToCategory(categoryId: String, task: Task) {
        repository.addTaskToCategory(categoryId, task) // Repository를 통해 할 일 추가 - 레포지토리에 할 일 추가 요청
    }

    // 할 일 상태 업데이트
    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        repository.updateTaskStatus(categoryId, taskId, isChecked) // Repository를 통해 할 일 상태 업데이트 - 레포지토리에 할 일 상태 업데이트 요청
    }

    // 카테고리 삭제
    fun deleteCategory(categoryId: String) {
        repository.deleteCategory(categoryId) // Repository를 통해 카테고리 삭제 - 레포지토리에 카테고리 삭제 요청
        fetchCategories() // 카테고리 목록 갱신 - 삭제 후 카테고리 목록 갱신
    }

    // 할 일 삭제
    fun deleteTask(categoryId: String, taskId: String) {
        repository.deleteTask(categoryId, taskId) // Repository를 통해 할 일 삭제 - 레포지토리에 할 일 삭제 요청
        fetchCategories() // 카테고리 목록 갱신 - 삭제 후 카테고리 목록 갱신
    }
}