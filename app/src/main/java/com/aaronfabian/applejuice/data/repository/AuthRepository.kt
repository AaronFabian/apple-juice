package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
   fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

   fun registerUser(email: String, password: String, name: String): Flow<Resource<AuthResult>>
}