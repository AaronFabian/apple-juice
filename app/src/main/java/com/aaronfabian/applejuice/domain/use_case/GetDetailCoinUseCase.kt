package com.aaronfabian.applejuice.domain.use_case

import com.aaronfabian.applejuice.data.remote.dto.toCoinDetailModel
import com.aaronfabian.applejuice.data.remote.dto.toCoinGraphModel
import com.aaronfabian.applejuice.data.remote.dto.toCoinTickerModel
import com.aaronfabian.applejuice.data.repository.CoinRepository
import com.aaronfabian.applejuice.data.repository.CoinRepositoryPaprika
import com.aaronfabian.applejuice.domain.model.CoinDetail
import com.aaronfabian.applejuice.domain.model.CoinGraph
import com.aaronfabian.applejuice.domain.model.CoinTicker
import com.aaronfabian.applejuice.utils.Resource
import com.aaronfabian.applejuice.utils.ResourceDoubleHtpp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDetailCoinUseCase @Inject constructor(
   private val repository: CoinRepositoryPaprika,
   private val repositoryRanking: CoinRepository
) {
//   operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
//      try {
//         this.emit(Resource.Loading<CoinDetail>())
//         val coin = repository.getCoinDetailById(coinId).toCoinDetailModel()
//         emit(Resource.Success<CoinDetail>(coin))
//      } catch (e: HttpException) {
//         emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred"))
//      } catch (e: IOException) {
//         emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection."))
//      }
//   }

   operator fun invoke(coinId: String): Flow<ResourceDoubleHtpp<CoinDetail, CoinTicker>> = flow {
      try {
         this.emit(ResourceDoubleHtpp.Loading())
         val coin = repository.getCoinDetailById(coinId).toCoinDetailModel()
         val ticker = repository.getCoinTickerById(coinId).toCoinTickerModel()
         emit(ResourceDoubleHtpp.Success<CoinDetail, CoinTicker>(coin, ticker))
      } catch (e: HttpException) {
         emit(
            ResourceDoubleHtpp.Error<CoinDetail, CoinTicker>(
               e.localizedMessage ?: "An unexpected error occurred",
            )
         )
      } catch (e: IOException) {
         emit(ResourceDoubleHtpp.Error<CoinDetail, CoinTicker>("Couldn't reach server. Check your internet connection."))
      }
   }

   fun getGraph(symbol: String): Flow<Resource<CoinGraph>> = flow {
      try {
         emit(Resource.Loading<CoinGraph>())
         val coin = repositoryRanking.getCoinGraphBySymbol(symbol).toCoinGraphModel()
         emit(Resource.Success<CoinGraph>(data = coin))
      } catch (e: HttpException) {
         emit(Resource.Error<CoinGraph>(e.localizedMessage ?: "An fatal error occurred"))
      } catch (e: IOException) {
         emit(Resource.Error<CoinGraph>("Unable to reach the server. Please check your internet connection."))
      }
   }


}