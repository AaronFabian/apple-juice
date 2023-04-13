package com.aaronfabian.applejuice.domain.model

import com.aaronfabian.applejuice.data.remote.dto.LinksX
import com.aaronfabian.applejuice.data.remote.dto.Position

data class CoinPeopleDetail(
   val description: String?,
   val id: String?,
   val links: LinksX?,
   val name: String?,
   val positions: List<Position>?,
)
