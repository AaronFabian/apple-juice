package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.CoinRankingApi
import com.aaronfabian.applejuice.data.remote.dto.CoinDto
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
   private val rankingApi: CoinRankingApi,
) : CoinRepository {

   override suspend fun getAllCoins(): CoinDto {
      return rankingApi.getCoinList()
      // don't forget tell retrofit this already return CoinList
   }
}