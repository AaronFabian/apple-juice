package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.CryptoCompareApi
import com.aaronfabian.applejuice.data.remote.dto.CoinNewsDto
import javax.inject.Inject

class CryptoCompareImpl @Inject constructor(
   private val cryptoCompareApi: CryptoCompareApi
) : CryptoCompareRepository {

   override suspend fun getLatestNews(): CoinNewsDto {
      return cryptoCompareApi.getLatestNews()
   }
}