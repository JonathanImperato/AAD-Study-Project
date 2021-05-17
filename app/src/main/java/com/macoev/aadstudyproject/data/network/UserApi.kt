package com.macoev.aadstudyproject.data.network

import com.macoev.aadstudyproject.data.entity.User
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject


const val END_POINT_USER_KTOR = "/api/user"

open class UserApi {

    @Inject lateinit var client: HttpClient

    suspend fun getUser(
        userId: String? = ""
    ): User = client.get("$END_POINT_USER_KTOR$userId")

    suspend fun saveUser(vararg user: User) {
        client.post<User>(END_POINT_USER_KTOR) {
            body = user
        }
    }

}