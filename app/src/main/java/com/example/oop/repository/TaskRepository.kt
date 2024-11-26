package com.example.oop.repository

import com.example.oop.data.Category
import com.example.oop.data.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TaskRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")

    fun addCategory(category: Category) {
        val categoryId = database.push().key ?: return
        database.child(categoryId).setValue(category.copy(id = categoryId))
    }

    fun addTaskToCategory(categoryId: String, task: Task) {
        val taskId = database.child(categoryId).child("tasks").push().key ?: return
        database.child(categoryId).child("tasks").child(taskId).setValue(task.copy(id = taskId))
    }

    fun deleteTask(categoryId: String, taskId: String) {
        database.child(categoryId).child("tasks").child(taskId).removeValue()
    }

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
        }.addOnFailureListener {
            // Handle failure
        }
    }

    fun updateTaskStatus(categoryId: String, taskId: String, isChecked: Boolean) {
        database.child(categoryId).child("tasks").child(taskId).child("checked").setValue(isChecked)
    }
}
