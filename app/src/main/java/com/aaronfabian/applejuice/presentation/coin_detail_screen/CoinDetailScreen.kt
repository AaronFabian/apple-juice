package com.aaronfabian.applejuice.presentation.coin_detail_screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinDetailScreen(
   viewModel: CoinDetailViewModel = hiltViewModel()
) {

   println(viewModel.state.value)
   Button(onClick = {

   }) {
      Text(text = "Detail Screen")
   }
}