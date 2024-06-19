package com.shubh.users.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubh.users.databinding.ItemUserBinding
import com.shubh.users.model.User

class UserAdapter(
    val userList: List<User>,
    val onClick: (position: Int) -> Unit,
    val onLongPress: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.nameTextView.text = currentUser.name
        holder.binding.emailTextView.text = currentUser.email
        holder.binding.phoneTextView.text = currentUser.phone
        holder.binding.userCardView.setOnClickListener {
            onClick(position)
        }

        holder.binding.userCardView.setOnLongClickListener {
            if (onLongPress != null) {
                onLongPress.invoke(position)
                true
            } else
                false
        }
    }
}