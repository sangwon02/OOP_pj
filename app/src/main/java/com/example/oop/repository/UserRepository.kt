package com.example.oop.repository

import android.util.Log
import com.example.oop.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Friends")    // "Users" 경로를 참조

    // 사용자 데이터를 가져오는 메소드, 매개변수 callback: 사용자 목록 반환
    fun getUsers(callback: (List<User>) -> Unit) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                callback(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
                Log.e("UserRepository", "Error fetching users: ${error.message}")
            }
        })
    }
}