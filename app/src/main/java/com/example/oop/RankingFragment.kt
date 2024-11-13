package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.data.User
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.databinding.FragmentRankingBinding
import com.example.oop.viewmodel.UserAdapter

class RankingFragment : Fragment() {
    val rankingList = arrayOf(
        User("박경훈"),
        User("이상원"),
        User("곽도혁"),
        User("대상혁"),
        User("대우제"),
        User("대오너"),
        User("대마유시"),
        User("대리아"),
        User("문경현")
    )

    lateinit var binding : FragmentRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)

        // Fragment 클래스 내부에서 this는 context로 간주되지 않아 requireContext()를 사용해야 한다.
        binding.recUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recUsers.adapter = UserAdapter(rankingList)

        return binding.root
    }
}