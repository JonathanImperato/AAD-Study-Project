package com.macoev.aadstudyproject.data.network

import com.macoev.aadstudyproject.data.entity.User
import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


const val END_POINT_USER_KTOR = "/api/user"

@KoinApiExtension
open class UserApi : KoinComponent {

    private val client: HttpClient by inject()

    suspend fun getUser(
        userId: String? = ""
    ): User = client.get("$END_POINT_USER_KTOR$userId")

    suspend fun saveUser(vararg user: User) {
        client.post<User>(END_POINT_USER_KTOR) {
            body = user
        }
    }

}