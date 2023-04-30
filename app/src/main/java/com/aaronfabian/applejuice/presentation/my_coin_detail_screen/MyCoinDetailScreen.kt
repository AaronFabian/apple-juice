package com.aaronfabian.applejuice.presentation.my_coin_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.my_coin_detail_screen.components.CoinInformation
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericLoadingUI
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.NavbarBackButton
import com.aaronfabian.applejuice.store.NavigationComposition

@Composable
fun MyCoinDetailScreen(
   navController: NavController,
   viewModel: MyCoinDetailScreenViewModel = hiltViewModel()
) {

   val cmp = NavigationComposition.current

   val state = viewModel.state.value
   val state2 = viewModel.state2.value

   LaunchedEffect(Unit) {
      viewModel.getMyCoin(cmp.user.uid)
   }

   Column {
      NavbarBackButton(text = "")

      if (!state2.isLoading) {
         CoinInformation(viewModel = viewModel, navController = navController)
      }

      if (state2.isLoading) {
         GenericLoadingUI()
      }
   }
}


