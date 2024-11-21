package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Category
import com.example.oop.data.Task
import com.example.oop.databinding.TaskItemBinding
import com.example.oop.databinding.CategoryItemBinding

class TaskAdapter(
    private val categories: List<Category>, // 카테고리 목록
    private val listener: OnTaskClickListener // 클릭 리스너 인터페이스
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0 // 카테고리 뷰 타입
        private const val VIEW_TYPE_TASK = 1 // 할 일 뷰 타입
    }

    interface OnTaskClickListener { // 추가할 할 일을 클릭했을 때 호출되는 인터페이스 정의
        fun onAddTaskClick(category: Category)
    }

    override fun getItemViewType(position: Int): Int {
        var currentPosition = 0
        for (category in categories) {
            if (currentPosition == position) {
                return VIEW_TYPE_CATEGORY // 현재 위치가 카테고리일 경우
            }
            currentPosition++ // 카테고리 위치 증가
            currentPosition += category.tasks.size // 할 일 수만큼 증가
        }
        return VIEW_TYPE_TASK // 할 일일 경우
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CATEGORY) {
            val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CategoryViewHolder(binding) // 카테고리 뷰 홀더 생성
        } else {
            val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TaskViewHolder(binding) // 할 일 뷰 홀더 생성
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentPosition = 0
        for (category in categories) {
            //카테고리 처리
            if (currentPosition == position) {
                (holder as CategoryViewHolder).bind(category) // 카테고리 바인딩
                holder.binding.addTaskButton.setOnClickListener { //추가 버튼을 눌렀을 때 이동
                    listener.onAddTaskClick(category) // 카테고리를 클릭했을 때 호출
                }
                return
            }
            currentPosition++ // 카테고리 위치 증가
            // 할 일 처리
            for (task in category.tasks) {
                if (currentPosition == position) {
                    (holder as TaskViewHolder).bind(task) // 할 일 바인딩
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
            binding.categoryName.text = category.name // 카테고리 이름 설정
        }
    }

    class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskName.text = task.name // 할 일 이름 설정
            binding.taskCheckbox.isChecked = task.isChecked // 체크박스 상태 설정
        }
    }
}
