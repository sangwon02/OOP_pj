package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.oop.databinding.FragmentFreiendaddBinding

class freiendaddFragment : Fragment() {
    // 나중에 메모리 누수를 방지하기 위해서
    private var binding: FragmentFreiendaddBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // xml 레이아웃을 바인딩 객체로 초기화
        binding = FragmentFreiendaddBinding.inflate(inflater, container, false)

        // 버튼 설정 함수 호출
        setupButtons()

        // 바인딩된 루트 뷰 반환
        return binding?.root
    }

    private fun setupButtons() {
        // btnBackToList: xml파일에 정의된 버튼 id
        // setOnClickListener: 버튼 눌렀을 때 작동하는 메소드
        binding?.btnBackToList?.setOnClickListener { // "btn_BackToList" 버튼 클릭 리스너
            findNavController().navigate(R.id.action_frg_freiendadd_to_frg_ranking)
        }
    }

    // Fragment가 화면에서 제거될 때 호출, 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding 객체 해제
        binding = null
    }
}