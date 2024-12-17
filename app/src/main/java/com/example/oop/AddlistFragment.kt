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

    private lateinit var taskViewModel: TaskViewModel // 할 일 데이터를 관리하는 ViewModel
    private lateinit var taskNameEditText: EditText // 할 일 이름을 입력받는 EditText
    private lateinit var addButton: Button // 할 일 추가 버튼
    private lateinit var dateText: TextView // 할 일 날짜를 표시하는 TextView
    private lateinit var categorySpinner: Spinner // 카테고리를 선택하는 Spinner
    private lateinit var selectedDate: String // 선택된 날짜를 저장하는 변수
    private lateinit var selectedCategoryId: String // 선택된 카테고리 ID를 저장하는 변수

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_addlist, container, false) // 프래그먼트 레이아웃을 inflate

        // ViewModel 초기화
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // View 초기화
        taskNameEditText = view.findViewById(R.id.list_name)
        addButton = view.findViewById(R.id.btn_addlist)
        dateText = view.findViewById(R.id.date_text)
        categorySpinner = view.findViewById(R.id.category_spinner)

        // 카테고리 스피너 설정
        setupCategorySpinner()

        // 초기 날짜 설정
        setDefaultDate()

        // 날짜 선택 TextView 클릭 시 DatePickerDialog 표시
        dateText.setOnClickListener { showDatePickerDialog() }

        // 할 일 추가 버튼 클릭 시
        addButton.setOnClickListener {
            val title = taskNameEditText.text.toString() // EditText에서 할 일 이름 가져오기
            if (title.isNotEmpty()) { // 할 일 이름이 비어있지 않으면
                val task = Task(name = title, createdAt = selectedDate) // 새로운 할 일 객체 생성
                taskViewModel.addTaskToCategory(selectedCategoryId, task) // TaskViewModel을 통해 할 일 추가
                Toast.makeText(requireContext(), "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show() // 토스트 메시지 표시
                findNavController().navigate(R.id.action_frg_listadd_to_frg_list) // ListFragment로 이동
            } else {
                Toast.makeText(requireContext(), "할 일을 입력하세요.", Toast.LENGTH_SHORT).show() // 토스트 메시지 표시
            }
        }
        return view
    }

    // 카테고리 스피너 설정
    private fun setupCategorySpinner() {
        val categoryViewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java) // CategoryViewModel 초기화

        // CategoryViewModel의 categories LiveData를 observe하여 변경 사항이 있을 때마다 Spinner 업데이트
        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.name } // 카테고리 이름 목록 생성
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames) // Spinner 어댑터 생성
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // 드롭다운 레이아웃 설정
            categorySpinner.adapter = adapter // Spinner에 어댑터 설정

            // Spinner 아이템 선택 리스너 설정
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedCategoryId = categories[position].id // 선택된 카테고리 ID 저장
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // 아무것도 선택되지 않았을 때 처리 (필요에 따라 추가)
                }
            }
        }
    }

    // 오늘 날짜를 기본값으로 설정
    private fun setDefaultDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        selectedDate = dateFormat.format(calendar.time)
        dateText.text = selectedDate
    }

    // DatePickerDialog를 표시하여 날짜를 선택
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                dateText.text = selectedDate
            }, year, month, day
        ).show()
    }
}