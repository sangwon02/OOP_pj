package com.example.oop


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.oop.data.Routine
import androidx.navigation.fragment.findNavController
import com.example.oop.databinding.FragmentRoutineaddBinding
import com.example.oop.databinding.FragmentRoutineBinding
import android.widget.Toast
import java.util.UUID
import com.example.oop.data.Routineadd



class RoutineaddFragment : Fragment() {
    private var binding: FragmentRoutineaddBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoutineaddBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 클릭 리스너
        binding?.btnBackToRoutine?.setOnClickListener {
            findNavController().popBackStack() // 이전 화면으로 돌아가기
        }

        // 루틴 추가 버튼 클릭 리스너
        binding?.btnBackToRoutine?.setOnClickListener {
            val routineName = binding?.routineeditText?.text.toString()
            if (routineName.isNotEmpty()) {
                // 루틴 추가 로직 (ViewModel을 사용하여 데이터 저장)
                val newRoutine = Routine(id = UUID.randomUUID().toString(), name = routineName)
                // 여기에 ViewModel을 통해 루틴을 추가하는 로직 필요

                // 루틴 화면으로 돌아가기
                findNavController().navigate(R.id.action_routineaddFragment_to_routineFragment)
            } else {
                Toast.makeText(requireContext(), "루틴 이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
