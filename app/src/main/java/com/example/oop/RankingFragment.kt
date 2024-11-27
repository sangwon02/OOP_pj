package com.example.oop

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.User
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.databinding.FragmentRankingBinding
import com.example.oop.viewmodel.UserAdapter
import androidx.recyclerview.widget.DividerItemDecoration

class RankingFragment : Fragment() {
    val rankingList = arrayOf(      // 리더보드 페이지의 사용자 포함 친구(사용자 객체 User.kt) 리스트입니다.
        User("박경훈"),
        User("이상원"),
        User("곽도혁"),
        User("대상혁"),
        User("대오너"),
        User("대마유시"),
        User("대리아"),
        User("문경현")
    )

    // fragment_ranking.xml의 뷰 요소를 코드에서 쉽게 다루게 합니다. 뷰 바인딩을 사용합니다.
    lateinit var binding : FragmentRankingBinding

    // Fragment가 호출될 때 호출되는 초기화 메소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Fragment의 UI를 생성하는 메소드
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // XML 레이아웃을 inflate(xml코드들을 객체화), binding 객체 초기화
        binding = FragmentRankingBinding.inflate(inflater, container, false)

        // recUsers: 리더보드 리사이클러뷰 id


        binding.recUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = UserAdapter(rankingList)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
        return binding.root



        /*
        // Fragment 클래스 내부에서 this는 context로 간주되지 않아 requireContext()를 사용해야 한다.
        binding.recUsers.layoutManager = LinearLayoutManager(requireContext())

        // binding.recUsers.setHasFixedSize(true)

        // UserAdapter 클래스를 이용하여 rankingList의 데이터를 리사이클러 뷰에 연결
        binding.recUsers.adapter = UserAdapter(rankingList)

        // Fragment의 루트 뷰 리턴
        return binding.root
         */

    }
}