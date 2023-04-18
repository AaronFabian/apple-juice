package com.aaronfabian.applejuice.data.remote.dto

data class Coin(
   val `24hVolume`: String? = "",
   val btcPrice: String? = "",
   val change: String? = "",
   val coinrankingUrl: String? = "",
   val color: String? = "",
   val iconUrl: String? = "",
   val listedAt: Int? = 0,
   val lowVolume: Boolean = false,
   val marketCap: String? = "",
   val name: String? = "",
   val price: String? = "",
   val rank: Int? = 0,
   val sparkline: List<String>? = emptyList(),
   val symbol: String? = "",
   val tier: Int? = 0,
   val uuid: String? = ""
)