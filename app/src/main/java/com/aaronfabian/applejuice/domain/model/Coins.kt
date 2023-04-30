package com.aaronfabian.applejuice.domain.model

data class Coins(
   var coinId: String = "",
   var coinUuid: String = "",
   val purchaseTime: String = "",
   val coinName: String = "",
   val ownerUid: String = "",
   val coinValue: Double = 0.0,
   var amount: Double = 0.0,
   val coinUri: String = "",
   val coinColor: String = "#FFFFFF"
)

// firebase model