package com.aaronfabian.applejuice.domain.model

import com.aaronfabian.applejuice.data.remote.dto.*

data class CoinDetail(
   val description: String?,
   val development_status: String?,
   val first_data_at: String?,
   val id: String?,
   val is_active: Boolean?,
   val is_new: Boolean?,
   val last_data_at: String?,
   val links: Links?,
   val links_extended: List<LinksExtended>,
   val message: String?,
   val name: String?,
   val org_structure: String?,
   val rank: Int?,
   val started_at: String?,
   val symbol: String?,
   val tags: List<Tag>?,
   val team: List<Team>?,
   val type: String?,
   val whitepaper: Whitepaper?,
   val logo: String,

   )
