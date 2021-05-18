package com.macoev.aadstudyproject.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int? = null,
    @ColumnInfo(name = "full_name")
    @SerialName("full_name")
    var fullName: String = "",
    var time: Long = System.currentTimeMillis()
) {

    companion object {
        @JvmStatic
        fun createRandom() = User().apply {
            time = System.currentTimeMillis()
            fullName = "Utente $time"
        }
    }

    override fun toString(): String {
        return "User(id=$id, fullName='$fullName', time='$time')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + fullName.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }
}
