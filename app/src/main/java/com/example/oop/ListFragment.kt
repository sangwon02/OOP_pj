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

    // 프래그먼트의 UI를 초기화하는 함수
    override fun onCreateView(
        inflater: LayoutInflater, // XML 레이아웃을 View 객체로 변환하는 LayoutInflater
        container: ViewGroup?, // 프래그먼트의 부모 ViewGroup
        savedInstanceState: Bundle? // 프래그먼트의 이전 상태 정보
    ): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false) // fragment_list 레이아웃을 inflate하여 View 객체 생성
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java) // TaskViewModel 객체 생성
        recyclerView = view.findViewById(R.id.taskRecyclerView) // RecyclerView 객체를 레이아웃에서 찾아 초기화
        todayDateTextView = view.findViewById(R.id.today_date) // TextView 객체를 레이아웃에서 찾아 초기화
        addCategoryButton = view.findViewById(R.id.addCategoryButton) // Button 객체를 레이아웃에서 찾아 초기화

        // CategoryAdapter 초기화
        adapter = CategoryAdapter(emptyList(), taskViewModel) { categoryId -> // 어댑터 생성 및 콜백 함수 설정
            val action = ListFragmentDirections.actionListFragmentToAddlistFragment(categoryId) // AddlistFragment로 이동하기 위한 action 생성
            findNavController().navigate(action) // Navigation 컴포넌트를 사용하여 AddlistFragment로 이동
        }

        recyclerView.adapter = adapter // RecyclerView에 어댑터 설정
        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // RecyclerView에 LinearLayoutManager 설정

        // taskViewModel의 categories LiveData를 observe하여 데이터 변경 시 UI 업데이트
        taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
            filterTasksByDate(categories) // 날짜 필터링 함수 호출
        }

        setDefaultDate() // 초기 날짜 설정

        // 날짜 선택 TextView 클릭 시 DatePickerDialog 표시
        todayDateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        // 카테고리 추가 버튼 클릭 시 CategoryaddFragment로 이동
        addCategoryButton.setOnClickListener {
            val action = ListFragmentDirections.actionFrgListToCategoryaddFragment()
            findNavController().navigate(action)
        }

        return view
    }

    // 오늘 날짜를 기본값으로 설정하는 함수
    private fun setDefaultDate() {
        val calendar = Calendar.getInstance() // 현재 날짜 정보를 가져오는 Calendar 객체 생성
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()) // 날짜 형식 지정
        selectedDate = dateFormat.format(calendar.time) // 현재 날짜를 지정된 형식의 문자열로 변환하여 저장
        todayDateTextView.text = selectedDate // TextView에 현재 날짜 표시
    }

    //날짜를 선택하는 함수
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance() // 현재 날짜 정보를 가져오는 Calendar 객체 생성
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog 생성 및 설정
        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay -> // 날짜 선택 리스너
                selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일" // 선택된 날짜를 지정된 형식의 문자열로 변환하여 저장
                todayDateTextView.text = selectedDate // TextView에 선택된 날짜 표시

                // 날짜 선택 후 필터링 적용
                taskViewModel.categories.value?.let { categories -> // 현재 카테고리 목록을 가져와서
                    filterTasksByDate(categories) // 필터링 함수 호출
                }
            }, year, month, day
        ).show()
    }

    // 선택된 날짜에 해당하는 할 일만 필터링하여 표시하는 함수
    private fun filterTasksByDate(categories: List<Category>) {
        val filteredCategories = categories.map { category -> // 각 카테고리에 대해
            val filteredTasks = category.tasks.filterValues { task -> // 할 일 목록을 필터링
                task.createdAt == selectedDate // 선택된 날짜와 할 일의 생성 날짜가 같은 경우만 필터링
            }
            category.copy(tasks = filteredTasks.toMutableMap()) // 필터링된 할 일 목록으로 새로운 카테고리 객체 생성
        }
        adapter.updateCategories(filteredCategories) // 어댑터에 필터링된 카테고리 목록 업데이트
    }
}