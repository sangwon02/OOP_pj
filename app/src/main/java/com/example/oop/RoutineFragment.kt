package com.example.oop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.data.Routine
import com.example.oop.databinding.FragmentRoutineBinding
import com.example.oop.RoutineAdapter

class RoutineFragment : Fragment() {
    private var binding: FragmentRoutineBinding? = null // 뷰 바인딩 객체
    private lateinit var routineAdapter: RoutineAdapter // 루틴 어댑터



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineBinding.inflate(inflater, container, false) // Fragment UI 생성
        return binding?.root // 생성된 뷰 반환

    }


    private fun setupRecyclerView() {
        binding?.routineRecyclerView?.layoutManager = LinearLayoutManager(requireContext()) // 수직 레이아웃 매니저 설정
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // 뷰 바인딩 객체 해제
    }
}
