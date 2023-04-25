package com.aaronfabian.applejuice.domain.model

data class User(
   val uid: String = "",
   var name: String = "",
   var email: String = "",
   var imageUri: String = "",
   var money: Double = 0.0
)

// firebase model