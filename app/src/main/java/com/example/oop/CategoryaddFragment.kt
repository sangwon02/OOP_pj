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

// 새로운 카테고리를 추가하는 화면
class CategoryaddFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel // 카테고리 데이터를 관리하는 ViewModel
    private lateinit var categoryNameEditText: EditText // 카테고리 이름을 입력받는 EditText
    private lateinit var addButton: Button // 카테고리 추가 버튼

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_categoryadd, container, false) // 프래그먼트 레이아웃을 inflate

        // ViewModel 초기화
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        // View 초기화
        categoryNameEditText = view.findViewById(R.id.category_name)
        addButton = view.findViewById(R.id.btn_addcate)

        // 카테고리 추가 버튼 클릭 시
        addButton.setOnClickListener {
            val categoryName = categoryNameEditText.text.toString() // EditText에서 카테고리 이름 가져오기
            if (categoryName.isNotEmpty()) { // 카테고리 이름이 비어있지 않으면
                val newCategory = Category(name = categoryName) // 새로운 카테고리 객체 생성
                categoryViewModel.addCategory(newCategory) // CategoryViewModel을 통해 카테고리 추가
                findNavController().navigate(R.id.action_categoryaddFragment_to_frg_list) // ListFragment로 이동
            }
        }
        return view
    }
}