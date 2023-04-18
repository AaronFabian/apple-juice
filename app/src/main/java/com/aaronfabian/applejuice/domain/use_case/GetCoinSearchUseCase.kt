package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinSearchModel
import com.aaronfabian.applejuice.data.repository.CoinRepository
import com.aaronfabian.applejuice.domain.model.CoinSearch
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinSearchUseCase @Inject constructor(
   private val repository: CoinRepository
) {

   operator fun invoke(query: String): Flow<Resource<CoinSearch>> = flow {
      try {

         emit(Resource.Loading<CoinSearch>())
         val coin = repository.getCoinBySearch(query).toCoinSearchModel()
         emit(Resource.Success<CoinSearch>(data = coin))
      } catch (e: HttpException) {
         emit(
            Resource.Error<CoinSearch>(
               message = e.localizedMessage ?: "An unexpected error occurred"
            )
         )
      } catch (e: IOException) {
         emit(Resource.Error<CoinSearch>("Please check your internet connection !"))
      }
   }
}