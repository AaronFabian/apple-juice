package com.aaronfabian.applejuice.data.remote.dto.di

import com.aaronfabian.applejuice.data.remote.CoinPaprikaApi
import com.aaronfabian.applejuice.data.remote.CoinRankingApi
import com.aaronfabian.applejuice.data.repository.*
import com.aaronfabian.applejuice.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun provideRanking(): CoinRankingApi {
      return Retrofit
         .Builder()
         .baseUrl(Constants.BASE_URL_COIN_RANKING)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(CoinRankingApi::class.java)
   } // for RepositoryImpl -> CoinRankingApi

   @Provides
   @Singleton
   fun provideCoinRepository(api: CoinRankingApi): CoinRepository {
      return CoinRepositoryImpl(api)
   } // for GetAllCoinsUseCase

   @Provides
   @Singleton
   fun providePaprika(): CoinPaprikaApi {
      return Retrofit
         .Builder()
         .baseUrl(Constants.BASE_URL_COIN_PAPRIKA)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(CoinPaprikaApi::class.java)
   } // for RepositoryImpl -> CoinPaprikaApi

   @Provides
   @Singleton
   fun provideCoinRepositoryPaprika(api: CoinPaprikaApi): CoinRepositoryPaprika {
      return CoinRepositoryPaprikaImpl(api)
   } // for GetDetailUseCase

   @Provides
   @Singleton
   fun providesFirebaseAuth() = FirebaseAuth.getInstance()

   @Provides
   @Singleton
   fun providesRepositoryImplementationImpl(firebaseAuth: FirebaseAuth): AuthRepository {
      return AuthRepositoryImpl(firebaseAuth)
   }
}