package com.macoev.roomsample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) val id: Int? = null, @ColumnInfo(name = "full_name") var fullName: String = ""){

    companion object {
        fun createRandom() = User().apply {
            fullName = "Utente ${System.currentTimeMillis()}"
        }
    }
}
