package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinNewsDto
import retrofit2.http.GET

interface CryptoCompareApi {
   @GET("/data/v2/news/?lang=EN")
   suspend fun getLatestNews(): CoinNewsDto
}