package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.example.oop.databinding.TaskItemBinding
import com.example.oop.databinding.CategoryItemBinding

class TaskAdapter(
    private val categories: List<Category>,
    private val listener: OnTaskClickListener // 클릭 리스너 인터페이스 추가
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_TASK = 1
    }

    interface OnTaskClickListener { // 인터페이스 정의
        fun onAddTaskClick(category: Category)
    }

    override fun getItemViewType(position: Int): Int {
        var currentPosition = 0
        for (category in categories) {
            if (currentPosition == position) {
                return VIEW_TYPE_CATEGORY
            }
            currentPosition++ // 카테고리 위치 증가
            currentPosition += category.tasks.size // 할 일 수만큼 증가
        }
        return VIEW_TYPE_TASK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CATEGORY) {
            val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CategoryViewHolder(binding)
        } else {
            val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TaskViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentPosition = 0
        for (category in categories) {
            // 카테고리 처리
            if (currentPosition == position) {
                (holder as CategoryViewHolder).bind(category)
                holder.binding.addTaskButton.setOnClickListener { // 클릭 시 이벤트 전파
                    listener.onAddTaskClick(category)
                }
                return
            }
            currentPosition++ // 카테고리 위치 증가
            // 할 일 처리
            for (task in category.tasks) {
                if (currentPosition == position) {
                    (holder as TaskViewHolder).bind(task)
                    return
                }
                currentPosition++ // 다음 할 일로 이동
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.sumOf { 1 + it.tasks.size } // 카테고리 + 할 일 수
    }

    class CategoryViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryName.text = category.name
        }
    }

    class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskName.text = task.name
            binding.taskCheckbox.isChecked = task.isChecked
        }
    }
}
