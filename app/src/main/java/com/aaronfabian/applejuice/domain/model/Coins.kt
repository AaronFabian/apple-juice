package com.aaronfabian.applejuice.domain.model

data class Coins(
   var coinId: String = "",
   val purchaseTime: String = "",
   val coinName: String = "",
   val ownerUid: String = "",
   var amount: Double = 0.0,
   val coinUri: String = "",
   val coinColor: String
)

// firebase model