package com.aaronfabian.applejuice.presentation.coin_detail_screen

import com.aaronfabian.applejuice.domain.model.CoinTicker

data class CoinTickerState(
   val isLoading: Boolean = false,
   val coin: CoinTicker? = null,
   val error: String = ""
)