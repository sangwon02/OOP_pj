package com.example.oop.repository

import android.util.Log
import com.example.oop.data.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepository {
    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")    // "Users" 경로를 참조

    // 사용자 데이터를 가져오는 메소드, 매개변수 callback: 사용자 목록 반환
    fun getUsers(callback: (List<User>) -> Unit) {
        // Task<DataSnapshot>를 반환
        db.get().addOnSuccessListener { dataSnapshot ->     // 가져온 데이터의 스냅샷
            // dataSnapshot의 자식 노드들을 순회하며 각 노드를 User 객체로 변환
            // mapNotNull을 사용하여 변환이 실패한 경우(즉, null인 경우)는 제외
            val users = dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.getValue(User::class.java) // 각 스냅샷을 User 클래스의 인스턴스로 변환
            }
            callback(users) // 변환된 사용자 리스트를 callback 함수에 전달하여 호출
        }.addOnFailureListener { exception ->
            Log.e("UserRepository", "Error getting users: ", exception)
        }
    }
}