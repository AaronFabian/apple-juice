package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinTicker

data class CoinTickerDto(
   val beta_value: Double,
   val circulating_supply: Int,
   val first_data_at: String,
   val id: String,
   val last_updated: String,
   val max_supply: Int,
   val name: String,
   val quotes: Quotes,
   val rank: Int,
   val symbol: String,
   val total_supply: Int
)

fun CoinTickerDto.toCoinTickerModel(): CoinTicker {
   return CoinTicker(
      beta_value = beta_value,
      circulating_supply = circulating_supply,
      first_data_at = first_data_at,
      id = id,
      last_updated = last_updated,
      max_supply = max_supply,
      name = name,
      quotes = quotes,
      rank = rank,
      symbol = symbol,
      total_supply = total_supply
   )
}