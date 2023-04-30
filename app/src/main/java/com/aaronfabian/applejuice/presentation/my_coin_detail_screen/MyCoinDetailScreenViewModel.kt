package com.aaronfabian.applejuice.presentation.my_coin_detail_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.domain.use_case.GetDetailCoinUseCase
import com.aaronfabian.applejuice.utils.Constants
import com.aaronfabian.applejuice.utils.FirebaseClass
import com.aaronfabian.applejuice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyCoinDetailScreenViewModel @Inject constructor(
   private val getOneCoinUseCase: GetDetailCoinUseCase,
   savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _state = mutableStateOf(MyCoinDetailScreenState())
   val state = _state

   private val _state2 = mutableStateOf(MyCoinDetailScreenState(isLoading = true))
   val state2 = _state2

   val coinInfoHashMap = mutableStateOf(HashMap<String, Any>())

   lateinit var coinUuid: String
   lateinit var coinId: String

   init {
      savedStateHandle.get<String>("coinUuid")?.let { coinUuid -> this.coinUuid = coinUuid }
      savedStateHandle.get<String>("coinId")?.let { coinId -> this.coinId = coinId }
      savedStateHandle.get<String>("coinUuid")?.let { coinUuid -> getCoin(coinUuid) }
   }

   private fun getCoin(coinUuid: String) {
      getOneCoinUseCase.getCoinOneCoinViaRanking(coinUuid).onEach { respond ->
         when (respond) {
            is Resource.Loading -> {
               _state.value = MyCoinDetailScreenState(isLoading = true)
            }
            is Resource.Success -> {
               _state.value = MyCoinDetailScreenState(oneCoin = respond.data)
            }
            is Resource.Error -> {
               _state.value =
                  MyCoinDetailScreenState(error = respond.message ?: "Fatal Error occurred !")
            }
         }
      }.launchIn(viewModelScope)
   }

   suspend fun getMyCoin(uid: String, coinId: String = this.coinId) {
      _state2.value = MyCoinDetailScreenState(isLoading = true)

      try {

         /*
         *
         * Data structure ->
         *     k       v
         *     name: Bitcoin (string)
         *     amount: 000 (double)
         *     value: 000  (double)
         *     coinUri: "" (string)
         *     color: #FFFFFF (string)
         *     list: List<Coins>
         */
         val docMap: HashMap<String, Any> = HashMap()

         val docs = FirebaseClass().getBoughtCoinAndTransaction(uid, coinId)
         for ((index, d) in docs.withIndex()) {
            val purchaseTime = d["purchaseTime"] as String
            val coinValue = d["coinValue"] as Double
            val coinAmount = d["amount"] as Double

            if (index == 0) {
               // val coinIds = d["coinId"]
               // val coinUuid = d["coinUuid"]
               // val coinUri = d["coinUri"]
               val coinName = d["coinName"] as String
               val coinColor = d["coinColor"] as String
               val coinUri = d["coinUri"] as String

               val coin = Coins(
                  purchaseTime = purchaseTime,
                  coinValue = coinValue,
                  amount = coinAmount,
               )

               docMap["name"] = coinName
               docMap["amount"] = coinAmount
               docMap["value"] = coinValue
               docMap["coinUri"] = coinUri
               docMap["color"] = coinColor
               docMap["list"] = mutableListOf<Coins>(coin)
            } else {
               val currentValue = docMap["value"] as Double
               val currentAmount = docMap["amount"] as Double

               val addedValue = currentValue + coinValue
               val addedAmount = currentAmount + coinAmount

               val coin = Coins(
                  purchaseTime = purchaseTime,
                  coinValue = coinValue,
                  amount = coinAmount,
               )

               docMap["value"] = addedValue
               docMap["amount"] = addedAmount
               (docMap["list"] as MutableList<Coins>).add(coin)
            }
         }

         coinInfoHashMap.value = docMap
         _state2.value = MyCoinDetailScreenState(isLoading = false)
      } catch (e: Exception) {
         e.printStackTrace()
         Log.e(Constants.ERROR_TAG, "Error at MyCoinDetailScreenViewModel :(")
         _state2.value = MyCoinDetailScreenState(error = "Something wrong :(")
      }
   }
}