package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.viewmodel.Repository
import com.example.oop.viewmodel.TaskAdapter

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Repository 인스턴스 생성
        repository = Repository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        displayData()
    }

    private fun setupRecyclerView() {
        binding?.taskRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(emptyList()) // 초기화
        binding?.taskRecyclerView?.adapter = taskAdapter
    }

    private fun displayData() {
        // Repository에서 더미 데이터 가져오기
        val tasks = repository.getTasks().value ?: emptyList()
        taskAdapter = TaskAdapter(tasks) // 더미 데이터 설정
        binding?.taskRecyclerView?.adapter = taskAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
