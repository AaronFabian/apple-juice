package com.aaronfabian.applejuice.domain.model

import com.aaronfabian.applejuice.data.remote.dto.DataXX

data class CoinGraph(
   val `data`: DataXX,
   val status: String
)
