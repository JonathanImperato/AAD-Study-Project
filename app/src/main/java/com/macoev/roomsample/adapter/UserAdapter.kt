package com.macoev.roomsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.macoev.roomsample.R
import com.macoev.roomsample.data.User
import com.macoev.roomsample.data.repository.Repository

class UserAdapter(private val repository: Repository, private val tap: (User) -> Unit) :
    RecyclerView.Adapter<UserViewHolder>() {

    private var data: ArrayList<User> = arrayListOf()

    init {
        repository.getAllUsers().observeForever {
            data.clear()
            data.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val u = data[position]
        holder.run {
            fullName.text = u.fullName
            delete.setOnClickListener { repository.delete(u) }
            itemView.setOnClickListener { tap(u) }
        }
    }

    override fun getItemCount() = data.size

}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fullName = itemView.findViewById<TextView>(R.id.fullName)
    val delete = itemView.findViewById<Button>(R.id.delete)
    val root = itemView.findViewById<ConstraintLayout>(R.id.root)

    companion object {
        fun create(parent: ViewGroup) = UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        )
    }
}