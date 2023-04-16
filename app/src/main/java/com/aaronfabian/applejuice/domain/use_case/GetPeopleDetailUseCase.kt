package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinPeopleDetail
import com.aaronfabian.applejuice.data.repository.CoinRepositoryPaprika
import com.aaronfabian.applejuice.domain.model.CoinPeopleDetail
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPeopleDetailUseCase @Inject constructor(
   private val repository: CoinRepositoryPaprika
) {
   operator fun invoke(coinId: String): Flow<Resource<CoinPeopleDetail>> = flow {
      try {
         this.emit(Resource.Loading<CoinPeopleDetail>())
         val coin = repository.getPeopleDetailById(coinId).toCoinPeopleDetail()
         emit(Resource.Success<CoinPeopleDetail>(coin))
      } catch (e: HttpException) {
         emit(
            Resource.Error<CoinPeopleDetail>(
               e.localizedMessage ?: "An unexpected error occurred"
            )
         )
      } catch (e: IOException) {
         emit(Resource.Error<CoinPeopleDetail>("Couldn't reach server. Check your internet connection."))
      }
   }
}