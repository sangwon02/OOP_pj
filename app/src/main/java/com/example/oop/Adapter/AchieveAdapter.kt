package com.example.oop.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Achieve
import com.example.oop.R


class AchieveAdapter(
    private val context: Context,
    private val achieveList: List<Achieve>,
    private val listener: OnTaskClickListener
) : RecyclerView.Adapter<AchieveAdapter.TaskViewHolder>() {

    interface OnTaskClickListener {
        fun onTaskChecked(achieve: Achieve, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(context)
        // item_achieve.xml을 사용하여 ViewHolder를 생성
        val view = inflater.inflate(R.layout.item_achieve, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = achieveList[position]
        holder.taskEditText.setText(task.taskName)
        holder.taskCheckBox.isChecked = task.isChecked

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            listener.onTaskChecked(task, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return achieveList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskEditText: EditText = itemView.findViewById(R.id.taskEditText2)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox2)
    }
}
