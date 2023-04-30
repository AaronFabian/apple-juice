package com.aaronfabian.applejuice.presentation.my_coin_detail_screen

import com.aaronfabian.applejuice.domain.model.OneCoin

data class MyCoinDetailScreenState(
   val isLoading: Boolean = false,
   val oneCoin: OneCoin? = null,
   val error: String = ""
)
