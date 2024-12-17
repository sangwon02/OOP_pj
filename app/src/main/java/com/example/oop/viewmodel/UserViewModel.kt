package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop.data.User
import com.example.oop.repository.UserRepository
import android.util.Log

// UserRepository를 주입받아 사용자 데이터를 관리
class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _friends = MutableLiveData<List<User>>()
    val friends: LiveData<List<User>> get() = _friends

    // 교수님 강의 내용 패턴이에용
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun loadFriends() {
        // UserRepository의 getUsers 메소드를 호출하여 사용자 목록 불ㄹㅓ옴
        repository.getUsers { users ->
            Log.d("UserViewModel", "Fetched Users: $users") // 로그 추가
            // 가져온 사용자 목록을 그대로 사용
            _friends.value = users // LiveData에 사용자 목록 설정
        }
    }
}