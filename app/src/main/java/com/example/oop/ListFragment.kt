package com.example.oop

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop.databinding.FragmentListBinding
import com.example.oop.viewmodel.ListViewModel
import com.example.oop.viewmodel.Repository
import com.example.oop.viewmodel.ViewModelFactory
import com.example.oop.viewmodel.TaskAdapter

import java.util.*

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null
    private lateinit var viewModel: ListViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Repository()
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePicker()
        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            taskAdapter = TaskAdapter(categories)
            binding?.recyclerView?.adapter = taskAdapter
        })
    }

    private fun setupDatePicker() {
        binding?.todayDate?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val dateString = String.format("%d월 %d일 %d년", selectedMonth + 1, selectedDay, selectedYear)
                binding?.todayDate?.text = dateString
            }, year, month, day).show()
        }
    }

    private fun setupButtons() {
        binding?.settingsButton?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_settingFragment)
        }

        binding?.addFriendButton?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_freiendaddFragment)
        }

        binding?.addCategoryButton?.setOnClickListener {
            findNavController().navigate(R.id.action_frg_list_to_categoryaddFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // 메모리 누수를 방지하기 위해 binding을 null로 설정
    }
}
