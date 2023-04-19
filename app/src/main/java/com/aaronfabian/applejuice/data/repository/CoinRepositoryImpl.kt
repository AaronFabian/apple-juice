package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.CoinRankingApi
import com.aaronfabian.applejuice.data.remote.dto.CoinDto
import com.aaronfabian.applejuice.data.remote.dto.CoinGraphDto
import com.aaronfabian.applejuice.data.remote.dto.CoinSearchDto
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
   private val rankingApi: CoinRankingApi,
) : CoinRepository {

   override suspend fun getAllCoins(): CoinDto {
      return rankingApi.getCoinList()
      // don't forget tell retrofit this already return CoinList
   }

   override suspend fun getCoinNextList(offset: String): CoinDto {
      return rankingApi.getCoinNextList(offset)
   }

   override suspend fun getCoinBySearch(query: String): CoinSearchDto {
      return rankingApi.getCoinBySearch(query)
   }

   override suspend fun getCoinGraphBySymbol(symbols: String): CoinGraphDto {
      return rankingApi.getCoinGraphBySymbol(symbols)
   }
}