package com.aaronfabian.applejuice.data.remote.dto

data class Market(
    val baseSymbol: String,
    val baseUuid: String,
    val exchangeIconUrl: String,
    val exchangeName: String,
    val exchangeUuid: String,
    val quoteSymbol: String,
    val quoteUuid: String,
    val recommended: Boolean,
    val uuid: String
)