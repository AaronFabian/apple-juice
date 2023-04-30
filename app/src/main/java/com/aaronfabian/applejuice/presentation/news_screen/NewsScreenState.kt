package com.aaronfabian.applejuice.presentation.news_screen

import com.aaronfabian.applejuice.domain.model.CoinNews

data class NewsScreenState(
   val isLoading: Boolean = false,
   val news: CoinNews? = null,
   val error: String = ""
)
