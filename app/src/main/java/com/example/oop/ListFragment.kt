package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class ListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var todayDateTextView: TextView
    private lateinit var selectedDate: String
    private lateinit var addCategoryButton: Button
    private lateinit var addFriendButton: Button // 추가된 부분

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        recyclerView = view.findViewById(R.id.taskRecyclerView)
        todayDateTextView = view.findViewById(R.id.today_date)
        addCategoryButton = view.findViewById(R.id.addCategoryButton)
        addFriendButton = view.findViewById(R.id.add_friend_button) // 추가된 부분

        // TaskViewModel을 CategoryAdapter에 전달합니다.
        adapter = CategoryAdapter(emptyList(), taskViewModel) { categoryId ->
            // 카테고리 ID를 넘겨서 할 일 추가 화면으로 이동
            val action = ListFragmentDirections.actionListFragmentToAddlistFragment(categoryId)
            findNavController().navigate(action)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.updateCategories(categories)
        }

        // 오늘 날짜로 초기화
        setDefaultDate()

        // 날짜 선택 다이얼로그
        todayDateTextView.setOnClickListener { showDatePickerDialog() }

        // 카테고리 추가 버튼 클릭 리스너
        addCategoryButton.setOnClickListener {
            val action = ListFragmentDirections.actionFrgListToCategoryaddFragment()
            findNavController().navigate(action)
        }

        // 친구 추가 버튼 클릭 리스너
        addFriendButton.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToFreiendaddFragment() // 수정된 부분
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
            // TODO: 선택된 날짜에 따른 할 일 필터링 로직 추가
        }, year, month, day).show()
    }
}
