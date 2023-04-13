package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinDetailModel
import com.aaronfabian.applejuice.data.repository.CoinRepositoryPaprika
import com.aaronfabian.applejuice.domain.model.CoinDetail
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailCoinUseCase @Inject constructor(
   private val repository: CoinRepositoryPaprika
) {
   operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
      try {
         this.emit(Resource.Loading<CoinDetail>())
         val coin = repository.getCoinDetailById(coinId).toCoinDetailModel()
         emit(Resource.Success<CoinDetail>(coin))
      } catch (e: HttpException) {
         emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occured"))
      } catch (e: IOException) {
         emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection."))
      }
   }


}