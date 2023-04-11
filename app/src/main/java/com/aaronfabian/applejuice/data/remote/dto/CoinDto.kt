package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinList

data class CoinDto(
    val `data`: Data,
    val status: String
)

fun CoinDto.toCoinListModel (): CoinList {
    return CoinList(
        data = data,
        status = status
    )
}