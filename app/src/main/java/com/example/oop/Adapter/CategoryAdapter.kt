package com.example.oop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Category
import com.example.oop.viewmodel.TaskViewModel

class CategoryAdapter(
    private var categories: List<Category>,
    private val taskViewModel: TaskViewModel,
    private val onAddTask: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val taskList: RecyclerView = itemView.findViewById(R.id.task_list)
        val addTaskButton: Button = itemView.findViewById(R.id.btnaddTask)
        val deleteCategoryButton: Button = itemView.findViewById(R.id.delete_category_button) // 카테고리 삭제 버튼

        fun bind(category: Category) {
            categoryName.text = category.name

            // 카테고리 삭제 버튼 클릭 리스너
            deleteCategoryButton.setOnClickListener {
                taskViewModel.deleteCategory(category.id)
            }

            // TaskAdapter 설정, 카테고리 ID와 삭제 함수 전달
            val taskAdapter = TaskAdapter(category.tasks.values.toList(), taskViewModel, category.id) { task ->
                taskViewModel.deleteTask(category.id, task.id) // 할 일 삭제
            }
            taskList.adapter = taskAdapter
            taskList.layoutManager = LinearLayoutManager(itemView.context)

            addTaskButton.setOnClickListener {
                onAddTask(category.id)
            }

            taskAdapter.updateTasks(category.tasks.values.toList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}