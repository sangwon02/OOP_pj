package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.Category
import com.example.oop.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

// 카테고리와 할 일 목록을 보여주는 화면
class ListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var todayDateTextView: TextView
    private lateinit var selectedDate: String
    private lateinit var addCategoryButton: Button
    private lateinit var addFriendButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        recyclerView = view.findViewById(R.id.taskRecyclerView)
        todayDateTextView = view.findViewById(R.id.today_date)
        addCategoryButton = view.findViewById(R.id.addCategoryButton)
        addFriendButton = view.findViewById(R.id.add_friend_button)

        adapter = CategoryAdapter(emptyList(), taskViewModel) { categoryId ->

            val action = ListFragmentDirections.actionListFragmentToAddlistFragment(categoryId)
            findNavController().navigate(action)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
            filterTasksByDate(categories) // 날짜 필터링 함수 호출
        }

        setDefaultDate()

        todayDateTextView.setOnClickListener {
            showDatePickerDialog()
            // 날짜 선택 후 필터링 적용
            taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
                filterTasksByDate(categories)
            }
        }

        addCategoryButton.setOnClickListener {
            val action = ListFragmentDirections.actionFrgListToCategoryaddFragment()
            findNavController().navigate(action)
        }

        addFriendButton.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToFreiendaddFragment()
            findNavController().navigate(action)
        }

        return view
    }

    private fun setDefaultDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        selectedDate = dateFormat.format(calendar.time)
        todayDateTextView.text = selectedDate
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
            todayDateTextView.text = selectedDate

            // 날짜 선택 후 필터링 적용
            taskViewModel.categories.value?.let { categories ->
                filterTasksByDate(categories)
            }
        }, year, month, day).show()
    }

    // 날짜에 맞는 할 일 필터링 함수
    private fun filterTasksByDate(categories: List<Category>) {
        val filteredCategories = categories.map { category ->
            val filteredTasks = category.tasks.filterValues { task ->
                task.createdAt == selectedDate
            }
            category.copy(tasks = filteredTasks.toMutableMap())
        }
        adapter.updateCategories(filteredCategories)
    }
}