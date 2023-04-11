package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinDetailDto

interface CoinRepositoryPaprika {
   suspend fun getCoinDetailById(coinId: String): CoinDetailDto
}