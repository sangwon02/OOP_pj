package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

class AddlistFragment : Fragment() {

    private lateinit var categorySpinner: Spinner
    private lateinit var taskNameEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 파라미터 처리 (필요시)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addlist, container, false)

        categorySpinner = view.findViewById(R.id.category_spinner)
        taskNameEditText = view.findViewById(R.id.task_name)
        dateTextView = view.findViewById(R.id.date_text)
        addButton = view.findViewById(R.id.add_button)

        setupCategorySpinner()
        setupDatePicker()

        addButton.setOnClickListener {
            // 할 일 추가 로직 구현
        }

        return view
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf("카테고리1", "카테고리2", "카테고리3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun setupDatePicker() {
        dateTextView.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // 날짜 문자열 생성
                val dateString = String.format("%d년 %d월 %d일", selectedYear, selectedMonth + 1, selectedDay)
                dateTextView.text = dateString
            }, year, month, day).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddlistFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}
