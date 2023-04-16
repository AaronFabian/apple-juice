package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinTagDetail

data class CoinTagDetailDto(

   val coin_counter: Int,
   val description: String,
   val ico_counter: Int,
   val id: String,
   val name: String,
   val type: String
)

fun CoinTagDetailDto.toCoinTagDetailModel(): CoinTagDetail {
   return CoinTagDetail(
      coin_counter = coin_counter,
      description = description,
      ico_counter = ico_counter,
      id = id,
      name = name,
      type = type
   )
}