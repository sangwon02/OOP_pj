package com.example.oop.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Achieve

class AchieveViewModel : ViewModel() {
    private val _achieveList = MutableLiveData<List<Achieve>>(emptyList())
    val achieveList: LiveData<List<Achieve>> get() = _achieveList

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        val initialData = listOf(
            Achieve("할 일 1", false),
            Achieve("할 일 2", false)
        )
        _achieveList.value = initialData
    }

    fun updateTask(achieve: Achieve, isChecked: Boolean) {
        val currentList = _achieveList.value ?: emptyList() // null 처리
        val updatedList = currentList.map  {
            if (it == achieve) it.copy(isChecked = isChecked) else it
        }
        _achieveList.value = updatedList
    }

    fun addTask(taskName: String) {
        val currentList = _achieveList.value ?: emptyList() // null 처리
        val updatedList = currentList + Achieve(taskName, false)
        _achieveList.value = updatedList
    }
}
