package com.aaronfabian.applejuice.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.data.repository.AuthRepository
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
   private val repository: AuthRepository
) : ViewModel() {

   private val _state = Channel<SignInState>()
   val state = _state.receiveAsFlow()

   fun loginUser(email: String, password: String) = viewModelScope.launch {
      repository.loginUser(email, password).collect { result ->
         when (result) {
            is Resource.Success -> {
               _state.send(SignInState(isSuccess = "Sign in success"))
            }
            is Resource.Loading -> {
               _state.send(SignInState(isLoading = true))
            }
            is Resource.Error -> {
               _state.send(SignInState(isError = result.message))
            }
         }
      }
   }


//   private val _state = MutableStateFlow(SignInState())
//   val state = _state.asStateFlow()

//   fun onSignInResult(result: SignInResult) {
//      _state.update {
//         it.copy(
//            isSignInSuccess = result.data != null,
//            signInError = result.errorMessage
//         )
//      }
//   }
//
//   fun resetState() {
//      _state.update { SignInState() }
//   }
}