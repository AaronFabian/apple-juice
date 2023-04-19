package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinGraph

data class CoinGraphDto(
   val `data`: DataXX,
   val status: String
)

fun CoinGraphDto.toCoinGraphModel(): CoinGraph {
   return CoinGraph(
      data = data,
      status = status
   )
}