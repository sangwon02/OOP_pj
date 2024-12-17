package com.example.oop.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Routine
import com.example.oop.databinding.RoutineItemBinding

class RoutineAdapter(
    private val routines: List<Routine>
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    inner class RoutineViewHolder(private val binding: RoutineItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            binding.routineName.text = routine.name // 루틴 이름 설정
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routines[position])
    }

    override fun getItemCount(): Int = routines.size
}
