package com.example.oop.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop.data.User
import com.example.oop.databinding.ListProfileBinding
import com.example.oop.R

// RecyclerView.Adapter를 상속하여 사용자 리스트를 리사이클러 뷰에 표시할 어댑터 역할
// Holder라는 뷰 홀더 클래스도 내부에 정의하여 각 사용자 항목을 재사용할 수 있게 설정
class UserAdapter(private var users: List<User>)
    : RecyclerView.Adapter<UserAdapter.Holder>() {

    // 새로운 뷰 홀더가 필요할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // parent: 리사이클러 뷰 전체
        val binding = ListProfileBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    // 리사이클러 뷰에 표시할 항목 수를 반환
    override fun getItemCount() = users.size

    // 지정된 위치에 있는 데이터를 뷰 홀더에 바인딩
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // Holder의 bind 메서드를 호출하여 users[position] 데이터를 전달하고, 뷰에 데이터를 표시
        holder.bind(users[position])
    }

    // 사용자 목록을 업데이트하는 메서드
    fun updateUsers(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged() // 데이터가 변경되었음을 어댑터에 알림
    }

    // ListProfileBinding을 통해 개별 항목의 뷰 요소에 접근
    class Holder(private val binding: ListProfileBinding): RecyclerView.ViewHolder(binding.root){
        // User 객체를 받아 각 뷰의 데이터와 연결
        fun bind(users: User) {
            binding.imageProfile.setImageResource(R.drawable.profileimage)  // 이미지
            binding.txtName.text = users.username                           // 이름
            binding.progressCircle2.progress = users.progression            // 진척도
        }
    }
}
