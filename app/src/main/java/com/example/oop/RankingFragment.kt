package com.example.oop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    // 어댑터를 클래스 수준에서 선언
    private lateinit var userAdapter: UserAdapter
    // ViewModel 인스턴스 생성
    private lateinit var viewModel: UserViewModel
    // fragment_ranking.xml의 뷰 요소를 코드에서 쉽게 다루게 합니다. 뷰 바인딩을 사용합니다.
    lateinit var binding : FragmentRankingBinding
    val sharedViewModel: UserViewModel by activityViewModels()

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRankingBinding.bind(view)

        userAdapter = UserAdapter(emptyList()) // 초기에는 빈 리스트로 초기화
        binding.recUsers.adapter = userAdapter
        binding.recUsers.layoutManager = LinearLayoutManager(requireContext())

        // UserRepository 인스턴스 생성
        val userRepository = UserRepository()

        // ViewModelFactory를 사용하여 ViewModel 초기화
        val factory = UserViewModelFactory(userRepository) // UserViewModelFactory 사용
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // ViewModel에서 사용자 데이터를 관찰하고 변경될 때마다 어댑터를 업데이트
        viewModel.friends.observe(viewLifecycleOwner) { users ->
            Log.d("RankingFragment", "Observed Users: $users")
            userAdapter.updateUsers(users)
        }

        binding.button.setOnClickListener { // "친구추가" 버튼 클릭 리스너
            findNavController().navigate(R.id.action_frg_ranking_to_frg_friendadd)
        }

        viewModel.loadFriends() // 데이터 로드
    }
}