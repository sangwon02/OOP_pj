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
    private val routineCategories: List<RoutineCategory>,
    private val listener: OnAddRoutineClickListener // 클릭 리스너 인터페이스 추가
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    interface OnAddRoutineClickListener { // 인터페이스 정의
        fun onAddRoutineClick(category: RoutineCategory)
    }

    class RoutineViewHolder(private val binding: RoutineCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routineCategory: RoutineCategory, listener: OnAddRoutineClickListener) {
            binding.routineCategoryName.text = routineCategory.category.name // 카테고리 이름 설정

            // 루틴 리스트를 표시하기 위한 RecyclerView 설정
            val routineAdapter = InnerRoutineAdapter(routineCategory.routines)
            binding.routineList.apply {
                adapter = routineAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }

            // add_routine_button 클릭 리스너 설정
            binding.addRoutineButton.setOnClickListener {
                listener.onAddRoutineClick(routineCategory) // 클릭 이벤트 전달
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routineCategories[position], listener) // 리스너 전달
    }

    override fun getItemCount(): Int {
        return routineCategories.size
    }
}

// 루틴을 표시하기 위한 내부 어댑터
class InnerRoutineAdapter(private val routines: List<Routine>) : RecyclerView.Adapter<InnerRoutineAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(private val binding: RoutineItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            binding.routineName.text = routine.name // 루틴 이름 설정
            // 필요한 추가 로직을 여기에 추가할 수 있습니다
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routines[position])
    }

    override fun getItemCount(): Int {
        return routines.size
    }
}
