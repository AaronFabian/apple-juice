package com.aaronfabian.applejuice.presentation.people_detail_screen

import com.aaronfabian.applejuice.domain.model.CoinPeopleDetail

data class PeopleDetailState(
   val isLoading: Boolean = false,
   val coin: CoinPeopleDetail? = null,
   val error: String = ""
)