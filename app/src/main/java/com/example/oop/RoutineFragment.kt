package com.example.oop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.databinding.FragmentRoutineBinding
import com.example.oop.viewmodel.RoutineAdapter
import com.example.oop.viewmodel.Repository

class RoutineFragment : Fragment() {
    private var binding: FragmentRoutineBinding? = null
    private lateinit var routineAdapter: RoutineAdapter
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Repository 인스턴스 생성
        repository = Repository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        displayData()

        binding?.addRoutineButton?.setOnClickListener {
            // 루틴 추가 화면으로 이동하는 로직을 추가
        }
    }

    private fun setupRecyclerView() {
        binding?.routineRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayData() {
        // Repository에서 더미 데이터 가져오기
        val routineCategories = repository.getRoutineCategories().value ?: emptyList()
        routineAdapter = RoutineAdapter(routineCategories) // 더미 데이터 설정
        binding?.routineRecyclerView?.adapter = routineAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
