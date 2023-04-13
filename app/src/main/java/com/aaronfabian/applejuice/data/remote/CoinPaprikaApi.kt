package com.aaronfabian.applejuice.data.remote

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

   @GET("/v1/coins/{coinId}")
   suspend fun getCoinDetailById(@Path("coinId") coinId: String): CoinDetailDto

   @GET("/v1/people/{peopleId}")
   suspend fun getPeopleDetailById(@Path("peopleId") peopleId: String): CoinPeopleDetailDto
}