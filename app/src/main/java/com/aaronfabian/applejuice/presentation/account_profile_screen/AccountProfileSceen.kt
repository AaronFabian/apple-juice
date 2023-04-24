package com.aaronfabian.applejuice.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.account_profile_screen.AccountProfileViewModel

@Composable
fun AccountProfileScreen(
   navController: NavController,
   viewModel: AccountProfileViewModel = hiltViewModel()
) {
   Text(text = "test")
}