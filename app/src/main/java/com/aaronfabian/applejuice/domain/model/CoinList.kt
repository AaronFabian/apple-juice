package com.aaronfabian.applejuice.domain.model

import com.aaronfabian.applejuice.data.remote.dto.Data

data class CoinList(
   val `data`: Data,
   val status: String
)
