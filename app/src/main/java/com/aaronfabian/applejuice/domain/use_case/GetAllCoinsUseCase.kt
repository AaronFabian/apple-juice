package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinListModel
import com.aaronfabian.applejuice.data.repository.CoinRepository
import com.aaronfabian.applejuice.domain.model.CoinList
import com.aaronfabian.applejuice.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCoinsUseCase @Inject constructor(
   private val repository: CoinRepository
) {
   operator fun invoke(): Flow<Resource<CoinList>> {
      return flow {
         try {

            emit(Resource.Loading())
            val fetchedData = repository.getAllCoins().toCoinListModel()
            emit(Resource.Success<CoinList>(fetchedData))
         } catch (e: HttpException) {
            emit(
               Resource.Error<CoinList>(
                  message = e.localizedMessage ?: "An unexpected error occurred !"
               )
            )
         } catch (e: IOException) {
            emit(Resource.Error<CoinList>(message = "Could not reach the server please check your internet connection !"))
         }
      }
   }

   fun getCoinNextList(offset: String): Flow<Resource<CoinList>> = flow {
      try {
         emit(Resource.Loading())
         val fetchedData = repository.getCoinNextList(offset).toCoinListModel()
         emit(Resource.Success<CoinList>(fetchedData))
      } catch (e: HttpException) {
         emit(
            Resource.Error<CoinList>(
               message = e.localizedMessage ?: "An unexpected error while fetch next page"
            )
         )
      } catch (e: IOException) {
         emit(Resource.Error<CoinList>(message = "Could not reach the server please check your internet connection !"))
      }
   }
}