package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinTagDetailModel
import com.aaronfabian.applejuice.data.repository.CoinRepositoryPaprika
import com.aaronfabian.applejuice.domain.model.CoinTagDetail
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetTagUseCase @Inject constructor(
   private val repository: CoinRepositoryPaprika
) {

   operator fun invoke(tagId: String): Flow<Resource<CoinTagDetail>> = flow {
      try {
         emit(Resource.Loading<CoinTagDetail>())
         val tag = repository.getCoinTagById(tagId).toCoinTagDetailModel()
         emit(Resource.Success<CoinTagDetail>(data = tag))
      } catch (e: HttpException) {
         emit(Resource.Error<CoinTagDetail>(e.localizedMessage ?: "An unexpected error occurred"))
      } catch (e: IOException) {
         emit(
            Resource.Error<CoinTagDetail>(
               e.message ?: "Cannot reach the server! please check the connectivity."
            )
         )
      }
   }
}