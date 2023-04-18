package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinDto
import com.aaronfabian.applejuice.data.remote.dto.CoinSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinRankingApi {

   @GET("/v2/coins?limit=100")
   suspend fun getCoinList(): CoinDto

   @GET("/v2/coins")
   suspend fun getCoinNextList(@Query("offset") offset: String): CoinDto

   @GET("/v2/search-suggestions")
   suspend fun getCoinBySearch(@Query("query") query: String): CoinSearchDto
}