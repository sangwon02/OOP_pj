package com.example.oop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop.repository.UserRepository

// UserViewModel 클래스가 기본 생성자를 가지고 있지 않거나, 매개변수가 있는 생성자를 사용하는 경우,
// ViewModelProvider가 인스턴스를 생성할 수 없음
// ViewModel은 기본 생성자를 사용해야 하거나, ViewModelProvider.Factory를 구현하여 인스턴스를 생성해야 함
// UserRepository를 매개변수로 줘야하므로 팩토리 생성
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}