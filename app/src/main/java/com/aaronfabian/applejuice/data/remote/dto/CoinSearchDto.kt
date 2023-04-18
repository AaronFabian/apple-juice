package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinSearch

data class CoinSearchDto(
   val `data`: DataX,
   val status: String
)

fun CoinSearchDto.toCoinSearchModel(): CoinSearch {
   return CoinSearch(
      data = data
   )
}