package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

   @GET("/v1/coins/{coinId}")
   suspend fun getCoinDetailById(@Path("coinId") coinId: String): CoinDetailDto
}