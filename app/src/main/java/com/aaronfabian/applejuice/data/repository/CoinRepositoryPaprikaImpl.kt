package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.CoinPaprikaApi
import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto
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