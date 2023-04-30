package com.aaronfabian.applejuice.presentation.account_profile_screen

import com.aaronfabian.applejuice.domain.model.Coins

data class AccountScreenState(
   val isLoading: Boolean = false,
   val isSuccess: HashMap<String, Coins>? = null,
   val isError: String? = "",
   val message: String? = null
)
