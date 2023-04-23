package com.aaronfabian.applejuice.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

   private val _isLoading = MutableStateFlow(true)
   val isLoading = _isLoading.asStateFlow()

   var currentUserId = ""
   var currentRoute = mutableStateOf(Screen.HomeScreen.route)

   init {
      viewModelScope.launch {
         delay(3000)
         checkIsSignIn()
      }
   }

   private fun checkIsSignIn() {
      val currentUser = FirebaseAuth.getInstance().currentUser
      if (currentUser != null) {
         currentUserId = currentUser.uid
      }
      if (currentUser == null) {
         currentRoute.value = Screen.SignInScreen.route
      }

      _isLoading.value = false
   }

   fun setRoute(route: String) {
      currentRoute.value = route
   }
}