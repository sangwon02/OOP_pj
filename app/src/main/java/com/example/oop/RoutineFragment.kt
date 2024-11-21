package com.example.oop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.data.RoutineCategory
import com.example.oop.databinding.FragmentRoutineBinding
import com.example.oop.viewmodel.RoutineAdapter
import com.example.oop.viewmodel.Repository

class RoutineFragment : Fragment(), RoutineAdapter.OnAddRoutineClickListener { // 루틴 추가 클릭 리스너 인터페이스 구현
    private var binding: FragmentRoutineBinding? = null // 뷰 바인딩 객체
    private lateinit var routineAdapter: RoutineAdapter // 루틴 어댑터
    private lateinit var repository: Repository // 데이터 리포지토리

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository() // Repository 인스턴스 생성
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(inflater, container, false) // Fragment UI 생성
        return binding?.root // 생성된 뷰 반환
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView() // RecyclerView 설정
        displayData()
    }

    private fun setupRecyclerView() {
        binding?.routineRecyclerView?.layoutManager = LinearLayoutManager(requireContext()) // 수직 레이아웃 매니저 설정
    }

    private fun displayData() {
        val routineCategories = repository.getRoutineCategories().value ?: emptyList() // 데이터 가져오기
        routineAdapter = RoutineAdapter(routineCategories, this) // 어댑터 초기화 및 클릭 리스너 전달
        binding?.routineRecyclerView?.adapter = routineAdapter // RecyclerView에 어댑터 설정
    }

    override fun onAddRoutineClick(category: RoutineCategory) {
        findNavController().navigate(R.id.action_routineFragment_to_routineaddFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
