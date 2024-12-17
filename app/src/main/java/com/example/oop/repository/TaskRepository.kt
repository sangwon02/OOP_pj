package com.example.oop.repository

import com.example.oop.data.Category
import com.example.oop.data.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Firebase와 상호작용하며 카테고리 및 할 일 데이터 관리를 담당하는 Repository 클래스
class TaskRepository {
    // categories에서  카테고리와 그 하위의 할일 데이터를 관리.
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")
    // Firebase Realtime Database의 "categories" 노드에 대한 레퍼런스를 가져옴

    // 새 카테고리를 Firebase에 추가. ID는 push()로 생성.
    fun addCategory(category: Category) {
        val categoryId = database.push().key ?: return // 카테고리 ID 생성 - Firebase에서 고유 ID를 생성
        database.child(categoryId).setValue(category.copy(id = categoryId))
        // Firebase에 카테고리 추가 - 생성된 ID를 포함하여 카테고리 데이터를 Firebase에 저장
    }

    // 특정 카테고리에 할 일을 추가.
    fun addTaskToCategory(categoryId: String, task: Task) {
        val taskId = database.child(categoryId).child("tasks").push().key ?: return
        // 할 일 ID 생성 - 해당 카테고리의 "tasks" 노드 아래에 고유 ID를 생성
        database.child(categoryId).child("tasks").child(taskId).setValue(task.copy(id = taskId))
        // Firebase에 할 일 추가 - 생성된 ID를 포함하여 할 일 데이터를 Firebase에 저장
    }

    //  카테고리 및 할일 데이터를 가져와 onResult 콜백으로 반환.
    fun getCategories(onResult: (List<Category>) -> Unit) {
        database.get().addOnSuccessListener { snapshot ->
            // Firebase에서 데이터 가져오기 - "categories" 노드의 모든 데이터를 비동기적으로 가져옴
            val categoriesList = mutableListOf<Category>() // 카테고리 목록 - 가져온 데이터를 저장할 리스트
            for (categorySnapshot in snapshot.children) { // 카테고리 스냅샷 순회 - 각 카테고리 데이터를 순회
                val category = categorySnapshot.getValue(Category::class.java)
                // 카테고리 객체로 변환 - 스냅샷 데이터를 Category 객체로 변환
                if (category != null) {
                    categorySnapshot.child("tasks").children.forEach { taskSnapshot ->
                        // 할 일 스냅샷 순회 - 각 카테고리에 속한 할 일들을 순회
                        val task = taskSnapshot.getValue(Task::class.java)
                        // 할 일 객체로 변환 - 스냅샷 데이터를 Task 객체로 변환
                        task?.let { category.tasks[taskSnapshot.key!!] = it }
                        // 카테고리에 할 일 추가 - 변환된 Task 객체를 해당 카테고리의 tasks 맵에 추가
                    }
                    categoriesList.add(category) // 카테고리 목록에 추가 - 완성된 카테고리 객체를 리스트에 추가
                }
            }
            onResult(categoriesList) // 콜백 함수 호출 - 가져온 카테고리 목록을 콜백 함수를 통해 반환
        }
    }

    // 카테고리 삭제
    fun deleteCategory(categoryId: String) {
        database.child(categoryId).removeValue() // Firebase에서 카테고리 삭제 - 해당 ID의 카테고리 데이터를 Firebase에서 삭제
    }

    // 할 일 삭제
    fun deleteTask(categoryId: String, taskId: String) {
        database.child(categoryId).child("tasks").child(taskId).removeValue()
        // Firebase에서 할 일 삭제 - 해당 카테고리의 "tasks" 노드 아래에서 해당 ID의 할 일 데이터를 삭제
    }

    // 할 일의 checked 상태를 업데이트
    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        database.child(categoryId).child("tasks").child(taskId).child("checked").setValue(isChecked)
        // Firebase에서 할 일 상태 업데이트 - 해당 할 일의 "checked" 값을 업데이트
    }
}