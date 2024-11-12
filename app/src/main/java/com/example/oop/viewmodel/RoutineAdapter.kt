package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.RoutineCategory
import com.example.oop.data.Routine
import com.example.oop.databinding.RoutineCategoryItemBinding // 카테고리 아이템 레이아웃을 위한 바인딩
import com.example.oop.databinding.RoutineItemBinding // 루틴 아이템 레이아웃을 위한 바인딩

class RoutineAdapter(private val routineCategories: List<RoutineCategory>) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(private val binding: RoutineCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routineCategory: RoutineCategory) {
            binding.routineCategoryName.text = routineCategory.category.name // 카테고리 이름 설정

            // 루틴 리스트를 표시하기 위한 RecyclerView 설정
            val routineAdapter = InnerRoutineAdapter(routineCategory.routines)
            binding.routineList.apply {
                adapter = routineAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routineCategories[position])
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
