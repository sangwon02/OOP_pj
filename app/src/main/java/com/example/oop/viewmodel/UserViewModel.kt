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

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    // 친구 목록을 업데이트하는 메소드
    fun updateFriends(newFriends: List<User>) {
        _friends.value = newFriends
    }

    /*
    fun loadUsers() {
        repository.getUsers { userList ->
            _users.value = userList
        }
    }
     */

    fun loadFriends() {
        // UserRepository의 getUsers 메소드를 호출하여 사용자 목록
        repository.getUsers { users ->
            Log.d("UserViewModel", "Fetched Users: $users") // 로그 추가
            // isFriend가 true인 사용자만 필터링
            val friendList = users.filter { it.isFriend == "true" }
            Log.d("UserViewModel", "Filtered Friend List: $friendList") // 로그 추가
            _friends.value = friendList
        }
    }
}