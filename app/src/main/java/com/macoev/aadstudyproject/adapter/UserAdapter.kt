package com.macoev.aadstudyproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.macoev.aadstudyproject.R
import com.macoev.aadstudyproject.data.entity.User
import com.macoev.aadstudyproject.data.repository.Repository

class UserAdapter(private val repository: Repository<User>, private val tap: (User) -> Unit) :
    PagedListAdapter<User, UserViewHolder>(DIFF_CALLBACK) {

    init {
        repository.getAllByTime()?.observeForever {
            submitList(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val u = getItem(position) ?: return
        holder.run {
            fullName.text = u.fullName
            delete.setOnClickListener { repository.delete(u) }
            itemView.setOnClickListener { tap(u) }
        }
    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fullName = itemView.findViewById<TextView>(R.id.fullName)
    val delete = itemView.findViewById<Button>(R.id.delete)

    companion object {
        fun create(parent: ViewGroup) = UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        )
    }
}