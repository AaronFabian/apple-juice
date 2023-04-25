package com.aaronfabian.applejuice.presentation.sign_in

import com.google.firebase.auth.FirebaseUser

data class SignInState(
   val isLoading: Boolean = false,
   val isSuccess: FirebaseUser? = null,
   val isError: String? = ""
)

//data class SignInState(
//   val isSignInSuccess: Boolean = false,
//   val signInError: String? = null,
//
//   )
