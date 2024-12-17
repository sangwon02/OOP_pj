package com.example.oop


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController // Navigation Component 사용
import com.example.oop.data.Routine
import com.example.oop.databinding.FragmentRoutineBinding
import com.example.oop.Adapter.RoutineAdapter
import android.util.Log // Log 추가
import android.widget.Toast


class RoutineFragment : Fragment() {
    private var binding: FragmentRoutineBinding? = null
    private lateinit var routineAdapter: RoutineAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // 루틴 추가 버튼 클릭 리스너 설정
        binding?.btnAddRoutine?.setOnClickListener {
            try {
                Log.d("RoutineFragment", "Add Routine Button Clicked") // 로그 추가
                val action = RoutineFragmentDirections.actionRoutineFragmentToRoutineaddFragment()
                findNavController().navigate(R.id.action_routineFragment_to_routineaddFragment)
            } catch (e: Exception) {
                Log.e("RoutineFragment", "Navigation error: ${e.message}")
                Toast.makeText(requireContext(), "오류가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.routineRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        routineAdapter = RoutineAdapter(emptyList())
        binding?.routineRecyclerView?.adapter = routineAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
