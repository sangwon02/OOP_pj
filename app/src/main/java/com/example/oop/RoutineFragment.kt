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

class RoutineFragment : Fragment(), RoutineAdapter.OnAddRoutineClickListener { // 인터페이스 구현
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
    }

    private fun setupRecyclerView() {
        binding?.routineRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayData() {
        // Repository에서 더미 데이터 가져오기
        val routineCategories = repository.getRoutineCategories().value ?: emptyList()
        routineAdapter = RoutineAdapter(routineCategories, this) // 클릭 리스너 전달
        binding?.routineRecyclerView?.adapter = routineAdapter
    }

    override fun onAddRoutineClick(category: RoutineCategory) {
        // 루틴 추가 화면으로 이동하는 로직
        findNavController().navigate(R.id.action_routineFragment_to_routineaddFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
