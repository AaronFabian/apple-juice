package com.aaronfabian.applejuice.presentation.tag_detail_screen

import com.aaronfabian.applejuice.domain.model.CoinTagDetail

data class TagDetailState(
   val isLoading: Boolean = false,
   val tag: CoinTagDetail? = null,
   val error: String = ""
)
