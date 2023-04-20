package com.aaronfabian.applejuice.presentation.sign_up

data class SignUpState(
   val isLoading: Boolean = false,
   val isSuccess: String? = "",
   val isError: String? = ""
)