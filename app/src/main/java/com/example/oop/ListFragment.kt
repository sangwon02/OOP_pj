package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.data.Category
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.viewmodel.Repository
import com.example.oop.viewmodel.TaskAdapter
import java.text.SimpleDateFormat
import java.util.*

class ListFragment : Fragment(), TaskAdapter.OnTaskClickListener { // 인터페이스 구현

    private var binding: FragmentListBinding? = null
    private lateinit var repository: Repository

    private lateinit var selectedDate: Calendar // 선택한 날짜를 저장할 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository()
        selectedDate = Calendar.getInstance() // 현재 날짜로 초기화
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        displayData()

        // 버튼 클릭 리스너 설정
        setupClickListeners()

        // 날짜 클릭 리스너 설정
        binding?.todayDate?.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setupClickListeners() {
        binding?.addCategoryButton?.setOnClickListener {
            findNavController().navigate(R.id.action_frg_list_to_categoryaddFragment)
        }

        binding?.addFriendButton?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_freiendaddFragment)
        }

        binding?.settingsButton?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_settingFragment)
        }
    }

    private fun setupRecyclerView() {
        binding?.taskRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayData() {
        repository.getCategories().observe(viewLifecycleOwner) { categories ->
            val adapter = TaskAdapter(categories, this) // 클릭 리스너 전달
            binding?.taskRecyclerView?.adapter = adapter
        }
    }

    private fun showDatePickerDialog() {
        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // 선택한 날짜로 Calendar 객체 업데이트
            selectedDate.set(Calendar.YEAR, selectedYear)
            selectedDate.set(Calendar.MONTH, selectedMonth)
            selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay)

            // 요일 계산
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.time)

            // 선택한 날짜를 TextView에 연도와 요일 포함하여 표시
            binding?.todayDate?.text = String.format("%04d년 %02d월 %02d일 (%s)", selectedYear, selectedMonth + 1, selectedDay, dayOfWeek)

            // 선택한 날짜에 맞는 할 일들을 필터링하여 표시하는 로직 추가
            // displayDataForSelectedDate(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    override fun onAddTaskClick(category: Category) {
        findNavController().navigate(R.id.action_listFragment_to_addlistFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
