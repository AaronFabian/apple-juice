package com.aaronfabian.applejuice.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {

   private val _isLoading = MutableStateFlow(true)
   val isLoading = _isLoading.asStateFlow()

   private var currentUserId = mutableStateOf("")
   var currentRoute = mutableStateOf(Screen.HomeScreen.route)

   var user = mutableStateOf(User())
   var isLoggedIn = mutableStateOf(false)

   init {
      viewModelScope.launch {
         delay(2500)
         checkIsSignedIn()
      }
   }

   private suspend fun checkIsSignedIn() {
      val currentUser = FirebaseAuth.getInstance().currentUser


      if (currentUser != null) {
         try {
            currentUserId.value = currentUser.uid
            val userDocument = FirebaseFirestore
               .getInstance()
               .collection(Constants.USERS_COLLECTION)
               .document(currentUser.uid)
               .get()
               .await()


            val user = userDocument.toObject(User::class.java)!!

            this.user.value = user
            this.isLoggedIn.value = true
         } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.ERROR_TAG, "something wrong error at MainViewModel")
         }
      }

      _isLoading.value = false
   }

   fun setRoute(route: String) {
      this.currentRoute.value = route
   }

   fun setUser(user: User) {
      this.user.value = user
   }

   fun setIsLoggedIn(isLoggedIn: Boolean) {
      this.isLoggedIn.value = isLoggedIn
   }
}