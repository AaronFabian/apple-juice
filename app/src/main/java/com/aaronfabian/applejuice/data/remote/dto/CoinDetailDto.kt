package com.aaronfabian.applejuice.data.remote.dto

import com.aaronfabian.applejuice.domain.model.CoinDetail

data class CoinDetailDto(
   val description: String,
   val development_status: String,
   val first_data_at: String,
   val hardware_wallet: Boolean,
   val hash_algorithm: String,
   val id: String,
   val is_active: Boolean,
   val is_new: Boolean,
   val last_data_at: String,
   val links: Links,
   val links_extended: List<LinksExtended>,
   val logo: String,
   val message: String,
   val name: String,
   val open_source: Boolean,
   val org_structure: String,
   val proof_type: String,
   val rank: Int,
   val started_at: String,
   val symbol: String,
   val tags: List<Tag>,
   val team: List<Team>,
   val type: String,
   val whitepaper: Whitepaper
)

fun CoinDetailDto.toCoinDetailModel(): CoinDetail {
   return CoinDetail(
      description = description,
      development_status = development_status,
      first_data_at = first_data_at,
      id = id,
      is_active = is_active,
      is_new = is_new,
      last_data_at = last_data_at,
      links = links,
      links_extended = links_extended,
      message = message,
      name = name,
      org_structure = org_structure,
      rank = rank,
      started_at = started_at,
      symbol = symbol,
      tags = tags,
      team = team,
      type = type,
      whitepaper = whitepaper
   )
}