package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinDto
import com.aaronfabian.applejuice.data.remote.dto.CoinGraphDto
import com.aaronfabian.applejuice.data.remote.dto.CoinSearchDto

interface CoinRepository {
   suspend fun getAllCoins(): CoinDto

   suspend fun getCoinNextList(offset: String): CoinDto

   suspend fun getCoinBySearch(query: String): CoinSearchDto

   suspend fun getCoinGraphBySymbol(symbols: String): CoinGraphDto

   suspend fun getCoinByUuid(uuid: String): CoinDto
}