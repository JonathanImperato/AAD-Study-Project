package com.macoev.aadstudyproject.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) val id: Int? = null, @ColumnInfo(name = "full_name") var fullName: String = ""){

    companion object {
        @JvmStatic
        fun createRandom() = User().apply {
            fullName = "Utente ${System.currentTimeMillis()}"
        }
    }

    override fun toString(): String {
        return "User(id=$id, fullName='$fullName')"
    }
}
