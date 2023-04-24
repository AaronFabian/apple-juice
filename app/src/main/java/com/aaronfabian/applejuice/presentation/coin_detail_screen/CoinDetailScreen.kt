package com.aaronfabian.applejuice.presentation.coin_detail_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.*
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericErrorUI
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericLoadingUI
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinDetailScreen(
   navController: NavController,
   viewModel: CoinDetailViewModel = hiltViewModel()
) {

   val state = viewModel.state.value
   val state2 = viewModel.state2.value
   val state3 = viewModel.state3.value


   var allowCoinDetail by remember {
      mutableStateOf(false)
   }

   LaunchedEffect(key1 = state, key2 = state2, key3 = state3) {
      if (state.coin != null) allowCoinDetail = true
   }

   if (allowCoinDetail) {
      CoinDetailScreenContent(state.coin!!, state2, state3, navController)
   }

   if (state.error.isNotBlank()) {
      GenericErrorUI(errorMessage = state.error)
   }

   if (state.isLoading) {
      GenericLoadingUI()
   }
}

