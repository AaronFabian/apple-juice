package com.aaronfabian.applejuice.presentation.coin_detail_screen

import com.aaronfabian.applejuice.domain.model.CoinDetail

data class CoinDetailState(
   val isLoading: Boolean = false,
   val coin: CoinDetail? = null,
   val error: String = ""
)