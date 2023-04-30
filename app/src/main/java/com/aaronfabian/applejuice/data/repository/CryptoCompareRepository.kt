package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinNewsDto

interface CryptoCompareRepository {

   suspend fun getLatestNews(): CoinNewsDto
}