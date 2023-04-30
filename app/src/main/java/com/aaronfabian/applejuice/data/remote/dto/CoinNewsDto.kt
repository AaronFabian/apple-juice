package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinNews

data class CoinNewsDto(
   val Data: List<DataXXX>,
   val HasWarning: Boolean,
   val Message: String,
   val Promoted: List<Any>,
   val RateLimit: RateLimit,
   val Type: Int
)

fun CoinNewsDto.toCoinNewsModel(): CoinNews {
   return CoinNews(
      data = Data
   )
}