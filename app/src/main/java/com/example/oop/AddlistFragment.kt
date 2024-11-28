package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop.data.Task
import com.example.oop.viewmodel.CategoryViewModel
import com.example.oop.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

// 새로운 할 일을 추가하는 화면
class AddlistFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskNameEditText: EditText
    private lateinit var addButton: Button
    private lateinit var dateText: TextView
    private lateinit var categorySpinner: Spinner
    private lateinit var selectedDate: String
    private lateinit var selectedCategoryId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_addlist, container, false)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskNameEditText = view.findViewById(R.id.list_name)
        addButton = view.findViewById(R.id.btn_addlist)
        dateText = view.findViewById(R.id.date_text)
        categorySpinner = view.findViewById(R.id.category_spinner)

        // 카테고리 스피너 초기화
        setupCategorySpinner()

        // 오늘 날짜로 초기화
        setDefaultDate()

        dateText.setOnClickListener { showDatePickerDialog() }

        // 할 일 추가 버튼 클릭 시
        addButton.setOnClickListener {
            val title = taskNameEditText.text.toString()
            if (title.isNotEmpty()) {
                // 할 일의 내용을 입력하고 선택한 날짜를 토대로 할 일 생성
                val task = Task(name = title, createdAt = selectedDate)

                // 할 일 추가
                taskViewModel.addTaskToCategory(selectedCategoryId, task)
                Toast.makeText(requireContext(), "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_frg_listadd_to_frg_list)
            } else {
                Toast.makeText(requireContext(), "할 일을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun setupCategorySpinner() {
        val categoryViewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter

            // 카테고리 선택 리스너 설정
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedCategoryId = categories[position].id
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    private fun setDefaultDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        selectedDate = dateFormat.format(calendar.time)
        dateText.text = selectedDate
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
            dateText.text = selectedDate
        }, year, month, day).show()
    }
}
