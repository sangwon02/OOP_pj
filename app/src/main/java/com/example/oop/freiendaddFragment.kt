package com.example.oop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.oop.data.User
import com.example.oop.databinding.FragmentFreiendaddBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class freiendaddFragment : Fragment() {
    private var binding: FragmentFreiendaddBinding? = null  // 메모리 누수 방지
    private val database = FirebaseDatabase.getInstance()   // Firebase Database 인스턴스
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

        binding?.btnAddFriend?.setOnClickListener {
            val friendId = binding?.inputFriendcode?.text.toString()
            moveUserToFriend(friendId)
        }
    }

    // 친구 추가 시 파이어베이스 User -> Friend로 옮기는 함수
    private fun moveUserToFriend(friendId: String) {
        val usersRef = database.reference.child("User")     // USer 노드 레퍼런스
        val friendsRef = database.reference.child("Friend") // Friend 노드 레퍼런스
        usersRef.orderByChild("id").equalTo(friendId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                // 데이터를 성공적으로 읽어올 경우
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {                        // 일치하는 유저가 있을 경우
                        for (userSnapshot in snapshot.children) {   // 혹시 모를 중복 방지
                            val user = userSnapshot.getValue(User::class.java)

                            // friends 노드에 user 데이터 추가
                            friendsRef.child(friendId).setValue(user)
                                .addOnSuccessListener {
                                    // 친구 추가 성공
                                    // users 노드에서 user 데이터 삭제
                                    userSnapshot.ref.removeValue()
                                        .addOnSuccessListener {
                                            // UI 업데이트 등 필요한 작업 수행
                                            Log.d("friendaddFragment", "User moved to Friend successfully!")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("friendaddFragment", "Error removing user", e)
                                        }
                                }
                                .addOnFailureListener { e ->
                                    // 친구 추가 실패
                                    Log.w("friendaddFragment", "Error adding friend", e)
                                }
                        }
                    } else {
                        // ID가 잘못되었거나 존재하지 않는 사용자입니다.
                        Log.w("friendaddFragment", "User not found with ID: $friendId")

                        // 존재하지 않는 사용자 메시지 띄우기
                        binding?.inputFriendcode?.error = "존재하지 않는 사용자입니다."
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("friendaddFragment", "Error getting documents: ", error.toException())
                }
            })
    }

    // Fragment가 화면에서 제거될 때 호출, 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding 객체 해제
        binding = null
    }
}