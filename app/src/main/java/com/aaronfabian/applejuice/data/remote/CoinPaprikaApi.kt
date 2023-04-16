package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTagDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTickerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

   @GET("/v1/coins/{coinId}")
   suspend fun getCoinDetailById(@Path("coinId") coinId: String): CoinDetailDto

   @GET("/v1/people/{peopleId}")
   suspend fun getPeopleDetailById(@Path("peopleId") peopleId: String): CoinPeopleDetailDto

   @GET("/v1/tickers/{tickerId}")
   suspend fun getCoinTickerById(@Path("tickerId") tickerId: String): CoinTickerDto

   @GET("/v1/tags/{tagsId}")
   suspend fun getTagDetailById(@Path("tagsId") tagId: String): CoinTagDetailDto
}