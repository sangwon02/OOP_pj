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

// Task를 RecyclerView에 표시하기 위한 어댑터
class TaskAdapter(
    private var tasks: List<Task>, // 할 일 목록
    private val taskViewModel: TaskViewModel, // TaskViewModel 인스턴스 - 할 일 관리
    private val categoryId: String, // 카테고리 ID - 할 일이 속한 카테고리 ID
    private val onDeleteTask: (Task) -> Unit // 할 일 삭제 콜백 함수 - 할 일 객체를 전달하여 삭제
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // TaskViewHolder 클래스 정의 - 각 할 일 아이템의 뷰를 담는 홀더
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.task_checkbox) // 할 일 완료 여부 체크박스
        val taskName: TextView = itemView.findViewById(R.id.list_name) // 할 일 이름 표시 TextView
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete) // 할 일 삭제 버튼

        // ViewHolder 바인딩 함수 - 할 일 데이터를 뷰에 바인딩
        fun bind(task: Task) {
            taskName.text = task.name // 할 일 이름 설정
            taskCheckbox.isChecked = task.checked // 체크박스 상태 설정

            // 체크박스 상태 변경 리스너
            taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                taskViewModel.updateTaskStatus(categoryId, task.id, isChecked) // ViewModel을 통해 할 일 상태 업데이트
            }

            // 삭제 버튼 클릭 리스너
            deleteButton.setOnClickListener {
                onDeleteTask(task) // 삭제 콜백 함수 호출 - 할 일 객체 전달
            }
        }
    }

    // ViewHolder 생성 함수 - 새로운 뷰 홀더를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false) // 레이아웃 인플레이트
        return TaskViewHolder(view) // ViewHolder 반환
    }

    // ViewHolder 바인딩 함수 - 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position] // 할 일 가져오기
        holder.bind(task) // ViewHolder에 데이터 바인딩 - 해당 위치의 할 일 데이터를 뷰 홀더에 바인딩
    }

    // 아이템 개수 반환 함수 - 어댑터가 관리하는 아이템 개수 반환
    override fun getItemCount(): Int = tasks.size

    // 할 일 목록 업데이트 함수 - 할 일 목록이 변경될 때 호출
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks // 할 일 목록 업데이트
        notifyDataSetChanged() // 변경 사항 알림 - 어댑터에 데이터 변경을 알려 뷰를 갱신
    }
}