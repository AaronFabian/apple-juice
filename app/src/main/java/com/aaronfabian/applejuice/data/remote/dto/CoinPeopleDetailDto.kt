package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinPeopleDetail

data class CoinPeopleDetailDto(
   val description: String,
   val id: String,
   val links: LinksX,
   val name: String,
   val positions: List<Position>,
   val teams_count: Int
)

fun CoinPeopleDetailDto.toCoinPeopleDetail(): CoinPeopleDetail {
   return CoinPeopleDetail(
      description = description,
      id = id,
      links = links,
      name = name,
      positions = positions
   )
}