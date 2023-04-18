package com.aaronfabian.applejuice.presentation.search_screen

import com.aaronfabian.applejuice.domain.model.CoinSearch

data class CoinSearchState(
   val isLoading: Boolean = false,
   val coin: CoinSearch? = null,
   val error: String = ""
)
