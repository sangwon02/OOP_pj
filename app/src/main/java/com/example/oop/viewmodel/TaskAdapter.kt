package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.R
import com.example.oop.data.Category
import com.example.oop.data.Task
import androidx.recyclerview.widget.LinearLayoutManager

class TaskAdapter(private val categories: List<Category>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.category_name)
        val editCategoryButton: Button = view.findViewById(R.id.edit_category_button)
        val addTaskButton: Button = view.findViewById(R.id.add_task_button)
        val taskList: RecyclerView = view.findViewById(R.id.task_list)

        fun bind(category: Category) {
            categoryName.text = category.name
            val taskAdapter = InnerTaskAdapter(category.tasks)
            taskList.layoutManager = LinearLayoutManager(itemView.context)
            taskList.adapter = taskAdapter

            addTaskButton.setOnClickListener {
                // 할 일 추가 로직 구현
            }

            editCategoryButton.setOnClickListener {
                // 카테고리 수정 로직 구현
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    class InnerTaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<InnerTaskAdapter.TaskViewHolder>() {
        inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val checkBox: CheckBox = view.findViewById(R.id.task_checkbox)

            fun bind(task: Task) {
                checkBox.text = task.name
                checkBox.isChecked = task.isCompleted
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
            return TaskViewHolder(view)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            holder.bind(tasks[position])
        }

        override fun getItemCount(): Int = tasks.size
    }
}
