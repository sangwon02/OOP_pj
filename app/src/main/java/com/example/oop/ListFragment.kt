package com.example.oop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.oop.databinding.FragmentListBinding


class ListFragment : Fragment() {
    var binding: FragmentListBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnlistadd?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addlistFragment)
        }
        binding?.btncategoryadd?.setOnClickListener {
            findNavController().navigate(R.id.action_frg_list_to_categoryaddFragment)
        }
        binding?.btnfriendadd?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_freiendaddFragment)
        }
    }

}