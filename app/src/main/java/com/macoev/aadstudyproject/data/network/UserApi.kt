package com.macoev.aadstudyproject.data.network

import com.macoev.aadstudyproject.data.entity.Login
import com.macoev.aadstudyproject.data.entity.User
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject


const val END_POINT_USER_KTOR = "/api/user"
const val END_POINT_LOGIN_KTOR = "/api/login"
const val BASE_URL = "https://giovanniscalise.my3cx.it"

open class UserApi @Inject constructor(private val client: HttpClient) {

    suspend fun getUser(
        userId: String? = ""
    ): User = client.get("$END_POINT_USER_KTOR$userId")

    suspend fun saveUser(vararg user: User) {
        client.post<User>(END_POINT_USER_KTOR) {
            body = user
        }
    }

    suspend fun login(auth: Login = Login("12", "XR2rDQkfkm")) {
        client.post<String>(BASE_URL + END_POINT_LOGIN_KTOR) {
            body = auth
        }
    }
}