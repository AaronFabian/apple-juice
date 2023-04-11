package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinDto
import retrofit2.http.GET

interface CoinRankingApi {

   @GET("/v2/coins")
   suspend fun getCoinList() : CoinDto
}