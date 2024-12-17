package com.example.oop.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Routineadd

class RoutineaddAdapter(
    private val context: Context,
    private val routineList: List<Routineadd>,
    private val listener: OnRoutineClickListener
) : RecyclerView.Adapter<RoutineaddAdapter.RoutineViewHolder>() {

    interface OnRoutineClickListener {
        fun onRoutineClick(routine: Routineadd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routineList[position]
        holder.nameTextView.text = routine.name
        holder.categoryTextView.text = routine.category

        holder.itemView.setOnClickListener {
            listener.onRoutineClick(routine)
        }
    }

    override fun getItemCount(): Int {
        return routineList.size
    }

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(android.R.id.text1)
        val categoryTextView: TextView = itemView.findViewById(android.R.id.text2)
    }
}
