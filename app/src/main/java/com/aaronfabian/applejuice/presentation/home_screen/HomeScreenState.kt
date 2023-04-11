package com.aaronfabian.applejuice.presentation.home_screen

import com.aaronfabian.applejuice.domain.model.CoinList

data class HomeScreenState(
   val isLoading: Boolean = false,
   val coin: CoinList? = null,
   val error: String = ""
)