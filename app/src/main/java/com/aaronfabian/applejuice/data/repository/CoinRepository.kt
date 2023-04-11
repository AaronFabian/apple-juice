package com.aaronfabian.applejuice.data.repository

import com.aaronfabian.applejuice.data.remote.dto.CoinDto

interface CoinRepository {
   suspend fun getAllCoins(): CoinDto
}