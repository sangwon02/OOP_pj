package com.example.oop


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Achieve
import com.example.oop.Adapter.AchieveAdapter

class AchieveFragment : Fragment(), AchieveAdapter.OnTaskClickListener {
    private val achieveList = mutableListOf<Achieve>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achive, container, false)
    }

    private fun loadSampleData() {
        achieveList.add(Achieve("할 일 1", false))
        achieveList.add(Achieve("할 일 2", false))
        // 여기서 achieveList를 사용하여 다른 작업을 수행할 수 있습니다.
    }

    override fun onTaskChecked(achieve: Achieve, isChecked: Boolean) {
        achieve.isChecked = isChecked
    }
}

