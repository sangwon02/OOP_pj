package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.RoutineCategory
import com.example.oop.data.Routine
import com.example.oop.databinding.RoutineCategoryItemBinding // 카테고리 아이템 레이아웃을 위한 바인딩
import com.example.oop.databinding.RoutineItemBinding // 루틴 아이템 레이아웃을 위한 바인딩

class RoutineAdapter(
    private val routineCategories: List<RoutineCategory>, // 루틴 카테고리 목록
    private val listener: OnAddRoutineClickListener // 클릭 리스너 인터페이스 추가
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() { // RecyclerView.Adapter 상속

    interface OnAddRoutineClickListener { // 루틴 추가 클릭 이벤트를 처리하는 인터페이스 정의
        fun onAddRoutineClick(category: RoutineCategory) // 루틴 추가 클릭 시 호출되는 메서드
    }

    class RoutineViewHolder(private val binding: RoutineCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routineCategory: RoutineCategory, listener: OnAddRoutineClickListener) {
            binding.routineCategoryName.text = routineCategory.category.name // 카테고리 이름 설정

            // 루틴 리스트를 표시하기 위한 RecyclerView 설정
            val routineAdapter = InnerRoutineAdapter(routineCategory.routines) // 내부 어댑터 생성
            binding.routineList.apply {
                adapter = routineAdapter // 내부 어댑터를 RecyclerView에 설정
                layoutManager = LinearLayoutManager(binding.root.context) // 레이아웃 매니저 설정
            }

            // add_routine_button 클릭 리스너 설정
            binding.addRoutineButton.setOnClickListener {
                listener.onAddRoutineClick(routineCategory) // 클릭 이벤트를 리스너에 전달
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        // 뷰 홀더 생성 및 바인딩
        val binding = RoutineCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding) // 생성한 바인딩으로 뷰 홀더 반환
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        // 특정 위치의 루틴 카테고리와 리스너를 바인딩
        holder.bind(routineCategories[position], listener) // 리스너 전달
    }

    override fun getItemCount(): Int {
        return routineCategories.size // 루틴 카테고리의 개수를 반환
    }
}

// 루틴을 표시하기 위한 내부 어댑터
class InnerRoutineAdapter(private val routines: List<Routine>) : RecyclerView.Adapter<InnerRoutineAdapter.RoutineViewHolder>() {
    class RoutineViewHolder(private val binding: RoutineItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            binding.routineName.text = routine.name // 루틴 이름 설정
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding) // 생성한 바인딩으로 뷰 홀더 반환
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routines[position]) // 루틴 데이터 바인딩
    }

    override fun getItemCount(): Int {
        return routines.size // 루틴의 개수를 반환
    }
}
