package com.example.oop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop.data.Category
import com.example.oop.viewmodel.CategoryViewModel

//새로운 카테고리를 추가하는 화면
class CategoryaddFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryNameEditText: EditText
    private lateinit var addButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_categoryadd, container, false)

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryNameEditText = view.findViewById(R.id.category_name)
        addButton = view.findViewById(R.id.btn_addcate)

        addButton.setOnClickListener {
            val categoryName = categoryNameEditText.text.toString()
            if (categoryName.isNotEmpty()) {
                val newCategory = Category(name = categoryName)
                // 카테고리 이름을 받아 CategoryViewModel의 addCategory 호출
                categoryViewModel.addCategory(newCategory)
                findNavController().navigate(R.id.action_categoryaddFragment_to_frg_list)
            }
        }
        return view
    }
}
