package com.example.oop.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.User
import com.example.oop.databinding.ListProfileBinding
import com.example.oop.R

class UserAdapter(val users: Array<User>)
    : RecyclerView.Adapter<UserAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListProfileBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(users[position])
    }

    class Holder(private val binding: ListProfileBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(users: User) {
            binding.imageProfile.setImageResource(R.drawable.profileimage)
            binding.txtName.text = users.name
        }
    }
}