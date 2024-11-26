package com.example.oop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Task
import com.example.oop.viewmodel.TaskViewModel

class TaskAdapter(
    private var tasks: List<Task>,
    private val taskViewModel: TaskViewModel, // TaskViewModel 추가
    private val categoryId: String, // 카테고리 ID 추가
    private val onDeleteTask: (Task) -> Unit // Task 삭제 리스너
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.task_checkbox)
        val taskName: TextView = itemView.findViewById(R.id.list_name)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete)

        fun bind(task: Task) {
            taskName.text = task.name
            taskCheckbox.isChecked = task.checked

            // 체크박스 상태 변경 시 Firebase에 업데이트
            taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                taskViewModel.updateTaskStatus(categoryId, task.id, isChecked) // 상태 업데이트 메서드 호출
            }

            deleteButton.setOnClickListener {
                onDeleteTask(task) // 삭제 리스너 호출
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task) // categoryId는 이제 TaskAdapter의 생성자에서 받습니다.
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
