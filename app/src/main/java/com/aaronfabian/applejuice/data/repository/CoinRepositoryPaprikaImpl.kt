package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.CoinPaprikaApi
import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTagDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTickerDto
import javax.inject.Inject

class CoinRepositoryPaprikaImpl @Inject constructor(
   private val paprikaApi: CoinPaprikaApi
) : CoinRepositoryPaprika {

   override suspend fun getCoinDetailById(coinId: String): CoinDetailDto {
      return paprikaApi.getCoinDetailById(coinId)
   }

   override suspend fun getPeopleDetailById(peopleId: String): CoinPeopleDetailDto {
      return paprikaApi.getPeopleDetailById(peopleId)
   }

   override suspend fun getCoinTickerById(coinId: String): CoinTickerDto {
      return paprikaApi.getCoinTickerById(coinId)
   }

   override suspend fun getCoinTagById(tagId: String): CoinTagDetailDto {
      return paprikaApi.getTagDetailById(tagId)
   }
}

//    normal way to call api
//      override suspend fun getCoinDetailById(coinId: String): CoinDetailDto {
//         val api = Retrofit
//            .Builder().baseUrl(Constants.BASE_URL_COIN_PAPRIKA)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(CoinPaprikaApi::class.java)
//
//         return api.getCoinDetailById(coinId)
//      }