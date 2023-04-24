package com.aaronfabian.applejuice.presentation.account_profile_screen

data class AccountScreenState(
   val isLoading: Boolean = false,
   val isSuccess: String? = "",
   val isError: String? = ""
)
