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
    private val taskViewModel: TaskViewModel, // TaskViewModel 추가
    private val onAddTask: (String) -> Unit // 할 일 추가 리스너
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val taskList: RecyclerView = itemView.findViewById(R.id.task_list)
        val addTaskButton: Button = itemView.findViewById(R.id.btnaddTask)

        fun bind(category: Category) {
            categoryName.text = category.name

            // TaskAdapter 설정, 카테고리 ID 전달
            val taskAdapter = TaskAdapter(category.tasks.values.toList(), taskViewModel, category.id) { task ->
                // 삭제 기능을 고려하지 않으므로 이 부분은 비워둡니다.
            }
            taskList.adapter = taskAdapter
            taskList.layoutManager = LinearLayoutManager(itemView.context)

            // 추가 버튼 클릭 시 AddListFragment로 이동
            addTaskButton.setOnClickListener {
                onAddTask(category.id) // 카테고리 ID를 넘겨줍니다.
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
