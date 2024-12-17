package com.example.oop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop.data.Routineadd
import com.example.oop.data.Routine


class RoutineaddViewModel : ViewModel() {
    private val _routines = MutableLiveData<List<Routine>>(emptyList())
    val routines: LiveData<List<Routine>> get() = _routines

    fun addRoutine(routine: Routine) {
        val currentList = _routines.value?.toMutableList() ?: mutableListOf()
        currentList.add(routine)
        _routines.value = currentList
    }
}
