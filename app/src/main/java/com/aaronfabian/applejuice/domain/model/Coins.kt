package com.aaronfabian.applejuice.domain.model

data class Coins(
   val coinId: String = "",
   val purchaseTime: String = "",
   val coinName: String = "",
   val ownerId: String = "",
   val amount: Double = 0.0,
   val coinUri: String = "",
)

// firebase model