package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.oop.databinding.FragmentFreiendaddBinding

class freiendaddFragment : Fragment() {
    private var binding: FragmentFreiendaddBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding을 사용하여 레이아웃을 바인딩합니다.
        binding = FragmentFreiendaddBinding.inflate(inflater, container, false)

        // 버튼 설정 함수 호출
        setupButtons()

        // 바인딩된 루트 뷰 반환
        return binding?.root
    }

    private fun setupButtons() {
        binding?.btnBackToList?.setOnClickListener {
            findNavController().navigate(R.id.action_btnlistadd_to_frg_list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding 객체 해제
        binding = null
    }
}