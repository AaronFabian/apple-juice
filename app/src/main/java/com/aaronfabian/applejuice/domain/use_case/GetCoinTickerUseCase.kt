package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinTickerModel
import com.aaronfabian.applejuice.data.repository.CoinRepositoryPaprika
import com.aaronfabian.applejuice.domain.model.CoinTicker
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinTickerUseCase @Inject constructor(
   private val repository: CoinRepositoryPaprika
) {
   operator fun invoke(tickerId: String): Flow<Resource<CoinTicker>> = flow {
      try {
         emit(Resource.Loading<CoinTicker>())
         val ticker = repository.getCoinTickerById(tickerId).toCoinTickerModel()
         emit(Resource.Success<CoinTicker>(ticker))
      } catch (e: HttpException) {
         emit(Resource.Error<CoinTicker>(e.localizedMessage ?: "An unexpected error occurred !"))
      } catch (e: IOException) {
         emit(Resource.Error<CoinTicker>(message = "Couldn't reach server. Check your internet connection."))
      }
   }
}