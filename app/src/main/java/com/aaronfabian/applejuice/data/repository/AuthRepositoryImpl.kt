package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
   private val firebaseAuth: FirebaseAuth // no need getInstance() since it's injected
) : AuthRepository {
   override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
      return flow {
         emit(Resource.Loading()) // this example no need addOnCompleteListener()
         val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
         emit(Resource.Success(result))
      }.catch {
         emit(Resource.Error(it.message.toString()))
      }
   }

   override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
      return flow {
         emit(Resource.Loading())
         val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
         emit(Resource.Success(result))
      }.catch {
         emit(Resource.Error(it.message.toString()))
      }
   }
}