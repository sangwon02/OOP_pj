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

// Category를 RecyclerView에 표시하기 위한 어댑터
class CategoryAdapter(
    private var categories: List<Category>,
    private val taskViewModel: TaskViewModel,
    private val onAddTask: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    //각 카테고리 아이템의 뷰를 담는 홀더
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val taskList: RecyclerView = itemView.findViewById(R.id.task_list)
        val addTaskButton: Button = itemView.findViewById(R.id.btnaddTask)
        val deleteCategoryButton: Button = itemView.findViewById(R.id.delete_category_button)

        // 카테고리 데이터를 뷰에 바인딩
        fun bind(category: Category) {
            categoryName.text = category.name // 카테고리 이름 설정

            // 카테고리 삭제 버튼 클릭 리스너
            deleteCategoryButton.setOnClickListener {
                taskViewModel.deleteCategory(category.id)
            }

            // TaskAdapter 설정, 카테고리 ID와 삭제 함수 전달
            // - 각 카테고리에 속한 할 일 목록을 표시하는 어댑터 생성
            val taskAdapter = TaskAdapter(category.tasks.values.toList(), taskViewModel, category.id) { task ->
                taskViewModel.deleteTask(category.id, task.id) // ViewModel을 통해 할 일 삭제
            }
            taskList.adapter = taskAdapter // RecyclerView에 어댑터 설정
            taskList.layoutManager = LinearLayoutManager(itemView.context) // RecyclerView에 레이아웃 매니저 설정

            // 할 일 추가 버튼 클릭 리스너
            addTaskButton.setOnClickListener {
                onAddTask(category.id) // 할 일 추가 콜백 함수 호출 - 카테고리 ID 전달
            }

            taskAdapter.updateTasks(category.tasks.values.toList()) // TaskAdapter 업데이트 - 할 일 목록 변경 시 어댑터에 알림
        }
    }

    // ViewHolder 생성 함수 - 새로운 뷰 홀더를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false) // 레이아웃 인플레이트
        return CategoryViewHolder(view) // ViewHolder 반환
    }

    // ViewHolder 바인딩 함수 - 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position]) // ViewHolder에 데이터 바인딩 - 해당 위치의 카테고리 데이터를 뷰 홀더에 바인딩
    }

    // 아이템 개수 반환 함수 - 어댑터가 관리하는 아이템 개수 반환
    override fun getItemCount(): Int = categories.size

    // 카테고리 목록 업데이트 함수 - 카테고리 목록이 변경될 때 호출
    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged() //어댑터에 데이터 변경을 알려 뷰 갱신
    }
}