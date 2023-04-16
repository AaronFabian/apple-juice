package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinPeopleDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTagDetailDto
import com.aaronfabian.applejuice.data.remote.dto.CoinTickerDto

interface CoinRepositoryPaprika {
   suspend fun getCoinDetailById(coinId: String): CoinDetailDto

   suspend fun getPeopleDetailById(peopleId: String): CoinPeopleDetailDto

   suspend fun getCoinTickerById(coinId: String): CoinTickerDto

   suspend fun getCoinTagById(tagId: String): CoinTagDetailDto
}