package com.example.oop.repository

import androidx.room.util.copy
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Firebase와 상호작용하며 카테고리 및 할 일을 추가 역할을 담당
class TaskRepository {
    // categories에서  카테고리와 그 하위의 할일 데이터를 관리.
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")

    // 새 카테고리를 Firebase에 추가. ID는 push()로 생성.
    fun addCategory(category: Category) {
        val categoryId = database.push().key ?: return
        database.child(categoryId).setValue(category.copy(id = categoryId))
    }

    // 특정 카테고리에 할 일을 추가.
    fun addTaskToCategory(categoryId: String, task: Task) {
        val taskId = database.child(categoryId).child("tasks").push().key ?: return
        database.child(categoryId).child("tasks").child(taskId).setValue(task.copy(id = taskId))
    }

    //  카테고리 및 할일 데이터를 가져와 onResult 콜백으로 반환.
    fun getCategories(onResult: (List<Category>) -> Unit) {
        database.get().addOnSuccessListener { snapshot ->
            val categoriesList = mutableListOf<Category>()
            for (categorySnapshot in snapshot.children) {
                val category = categorySnapshot.getValue(Category::class.java)
                if (category != null) {
                    categorySnapshot.child("tasks").children.forEach { taskSnapshot ->
                        val task = taskSnapshot.getValue(Task::class.java)
                        task?.let { category.tasks[taskSnapshot.key!!] = it }
                    }
                    categoriesList.add(category)
                }
            }
            onResult(categoriesList)
        }
    }

    // 할 일의 checked 상태를 업데이트
    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        database.child(categoryId).child("tasks").child(taskId).child("checked").setValue(isChecked)
    }
}