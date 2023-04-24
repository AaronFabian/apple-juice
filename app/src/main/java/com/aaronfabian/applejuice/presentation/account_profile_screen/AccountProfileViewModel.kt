package com.aaronfabian.applejuice.presentation.account_profile_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountProfileViewModel @Inject constructor() : ViewModel() {

   private var _state = mutableStateOf(AccountScreenState())
   val state = _state

   init {
      getUserCoin()
   }

   private fun getUserCoin() {}
}