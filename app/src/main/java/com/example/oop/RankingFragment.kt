package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.User
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.databinding.FragmentRankingBinding
import com.example.oop.viewmodel.UserAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.oop.repository.UserRepository
import com.example.oop.viewmodel.UserViewModel
import com.example.oop.viewmodel.UserViewModelFactory

class RankingFragment : Fragment() {
    /*
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
     */

    // 어댑터를 클래스 수준에서 선언
    private lateinit var userAdapter: UserAdapter
    // ViewModel 인스턴스 생성
    private lateinit var viewModel: UserViewModel
    // fragment_ranking.xml의 뷰 요소를 코드에서 쉽게 다루게 합니다. 뷰 바인딩을 사용합니다.
    lateinit var binding : FragmentRankingBinding

    // Fragment가 호출될 때 호출되는 초기화 메소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // UserRepository 인스턴스 생성
        val userRepository = UserRepository() // UserRepository의 생성 방법에 따라 다를 수 있습니다.

        // ViewModelFactory를 사용하여 ViewModel 초기화
        val factory = UserViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        viewModel.loadFriends()
    }

    // Fragment의 UI를 생성하는 메소드
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // XML 레이아웃을 inflate(xml코드들을 객체화), binding 객체 초기화
        binding = FragmentRankingBinding.inflate(inflater, container, false)

        // recUsers: 리더보드 리사이클러뷰 id
        binding.recUsers.apply {
            setHasFixedSize(true)
            // Fragment 클래스 내부에서 this는 context로 간주되지 않아 requireContext()를 사용해야 한다.
            layoutManager = LinearLayoutManager(requireContext())

            // UserAdapter 클래스를 이용하여 rankingList의 데이터를 리사이클러 뷰에 연결
            // adapter = UserAdapter(rankingList)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        // UserAdapter 초기화 및 RecyclerView에 설정
        userAdapter = UserAdapter(emptyList()) // 초기에는 빈 리스트로 설정
        binding.recUsers.adapter = userAdapter // RecyclerView에 어댑터 설정

        // 친구 목록을 관찰하고 RecyclerView에 업데이트
        viewModel.friends.observe(viewLifecycleOwner) { friendList ->
            // UserAdapter에 친구 목록을 설정
            (binding.recUsers.adapter as UserAdapter).updateUsers(friendList)
        }

        // Fragment의 루트 뷰 리턴
        return binding.root
    }
}