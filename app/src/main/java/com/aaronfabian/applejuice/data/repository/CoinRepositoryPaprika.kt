package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto

interface CoinRepositoryPaprika {
   suspend fun getCoinDetailById(coinId: String): CoinDetailDto

   suspend fun getPeopleDetailById(peopleId: String): CoinPeopleDetailDto
}