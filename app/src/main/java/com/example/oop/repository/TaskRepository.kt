package com.example.oop.repository

import com.example.oop.data.Category
import com.example.oop.data.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Firebase의 카테고리 및 할 일 데이터 관리
class TaskRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")
    // Firebase의 "categories" 노드에 대한 레퍼런스를 가져옴

    // 새 카테고리를 Firebase에 추가. ID는 push()로 생성
    fun addCategory(category: Category) {
        val categoryId = database.push().key ?: return // Firebase에서 제공하는 push 메서드
        database.child(categoryId).setValue(category.copy(id = categoryId))  // 생성된 ID와 카테고리 데이터를 Firebase에 저장
    }

    // 특정 카테고리에 할 일을 추가
    fun addTaskToCategory(categoryId: String, task: Task) {
        val taskId = database.child(categoryId).child("tasks").push().key ?: return
        database.child(categoryId).child("tasks").child(taskId).setValue(task.copy(id = taskId))
    }

    //  카테고리 및 할일 데이터를 가져와 onResult 콜백으로 반환.
    fun getCategories(onResult: (List<Category>) -> Unit) {
        database.get().addOnSuccessListener { snapshot ->
            val categoriesList = mutableListOf<Category>() // 카테고리를 저장할 리스트 선언
            for (categorySnapshot in snapshot.children) { //카테고리 데이터를 순회
                val category = categorySnapshot.getValue(Category::class.java) //Category 객체로 변환
                if (category != null) {
                    categorySnapshot.child("tasks").children.forEach { taskSnapshot ->
                        val task = taskSnapshot.getValue(Task::class.java) // task로 변환
                        task?.let { category.tasks[taskSnapshot.key!!] = it }
                        // 카테고리에 할 일 추가 - Task를 해당 카테고리의 tasks 맵에 추가
                    }
                    categoriesList.add(category) // 카테고리 목록에 추가
                }
            }
            onResult(categoriesList) //가져온 카테고리 목록을 콜백 함수를 통해 반환
        }
    }

    // 카테고리 삭제
    fun deleteCategory(categoryId: String) {
        database.child(categoryId).removeValue()
    }

    // 할 일 삭제
    fun deleteTask(categoryId: String, taskId: String) {
        database.child(categoryId).child("tasks").child(taskId).removeValue()
    }

    // 할 일의 checked 상태를 업데이트
    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        database.child(categoryId).child("tasks").child(taskId).child("checked").setValue(isChecked)
    }
}