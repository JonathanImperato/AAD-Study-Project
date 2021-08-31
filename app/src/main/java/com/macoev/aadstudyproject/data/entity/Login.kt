package com.macoev.aadstudyproject.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Login(val username: String, val password: String)