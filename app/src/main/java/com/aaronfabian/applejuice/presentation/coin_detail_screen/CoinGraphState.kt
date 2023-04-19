package com.aaronfabian.applejuice.presentation.coin_detail_screen

import com.aaronfabian.applejuice.domain.model.CoinGraph

data class CoinGraphState(
   val isLoading: Boolean = false,
   val coin: CoinGraph? = null,
   val error: String = ""
)