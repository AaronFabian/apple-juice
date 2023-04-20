package com.aaronfabian.applejuice.presentation.sign_in

data class SignInState(
   val isLoading: Boolean = false,
   val isSuccess: String? = "",
   val isError: String? = ""
)

//data class SignInState(
//   val isSignInSuccess: Boolean = false,
//   val signInError: String? = null,
//
//   )
