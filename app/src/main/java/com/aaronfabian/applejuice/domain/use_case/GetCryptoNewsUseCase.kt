package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinNewsModel
import com.aaronfabian.applejuice.data.repository.CryptoCompareRepository
import com.aaronfabian.applejuice.domain.model.CoinNews
import com.aaronfabian.applejuice.utils.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCryptoNewsUseCase @Inject constructor(
   private val repository: CryptoCompareRepository
) {
   operator fun invoke(): Flow<Resource<CoinNews>> = flow {
      try {
         emit(Resource.Loading<CoinNews>())
         val news = repository.getLatestNews().toCoinNewsModel()
         emit(Resource.Success<CoinNews>(data = news))
      } catch (e: HttpException) {
         emit(Resource.Error<CoinNews>(e.localizedMessage ?: "An unexpected error occurred !"))
      } catch (e: IOException) {
         emit(Resource.Error<CoinNews>(message = "Couldn't reach server. Check your internet connection."))
      }
   }
}