package com.aaronfabian.applejuice.presentation.account_profile_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.utils.Constants
import com.aaronfabian.applejuice.utils.FirebaseClass
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class AccountProfileViewModel @Inject constructor() : ViewModel() {

   private var _state = mutableStateOf(AccountScreenState())
   val state = _state

   private var _changedNameState = mutableStateOf(AccountScreenState())
   var changedNameState = _changedNameState

   private var _depositMoneyState = mutableStateOf(AccountScreenState())
   var depositMoneyState = _depositMoneyState

   init {

   }

   suspend fun getUserCoin(uid: String) {
      this._state.value = AccountScreenState(isLoading = true)

      try {
         val documentCoins = FirebaseClass().getUserCoin(uid)
         val coinsList: HashMap<String, Coins> = HashMap()


         for (c in documentCoins.documents) {
            if (c["coinId"] in coinsList) {
               val fetchedAmount = c["amount"] as Double
               val currentAmount = coinsList[c["coinId"]]!!.amount
               val addedAmount = currentAmount + fetchedAmount

               coinsList[c["coinId"]]!!.amount = addedAmount
            } else {
               val coin = Coins(
                  coinId = c["coinId"] as String,
                  coinUuid = c["coinUuid"] as String,
                  coinName = c["coinName"] as String,
                  coinUri = c["coinUri"] as String,
                  coinColor = c["coinColor"] as String,
                  ownerUid = c["ownerUid"] as String,
                  amount = c["amount"] as Double,
                  purchaseTime = c["purchaseTime"] as String
               )

               coinsList[c["coinId"] as String] = coin
            }
         }

         this._state.value = AccountScreenState(isSuccess = coinsList)
      } catch (e: Exception) {
         e.printStackTrace()
         Log.e(Constants.ERROR_TAG_FIREBASE, "Error at AccountViewModel :(")
         this._state.value = AccountScreenState(isError = "Error :(")
      } catch (e: CancellationException) {
         e.printStackTrace()
         Log.e(Constants.ERROR_TAG, "Error at AccountViewModel (Cancellation exception) :(")
         this._state.value = AccountScreenState(isError = "Error :(")
      }
   }

   suspend fun handleChangeName(user: User, changedName: String) {
      try {
         this._changedNameState.value = AccountScreenState(isLoading = true)

         FirebaseClass().updateUserName(user.uid, changedName)

         this._changedNameState.value = AccountScreenState(message = "")
      } catch (e: Exception) {
         this._changedNameState.value = AccountScreenState(isError = "Error :(")
         throw Exception()
      }
   }

   suspend fun handleDepositMoney(user: User, amount: String) {
      try {
         this._depositMoneyState.value = AccountScreenState(isLoading = true)

         FirebaseClass().updateDepositMoney(user, amount.toDouble())

         this._depositMoneyState.value = AccountScreenState(message = "")
      } catch (e: Exception) {
         this._depositMoneyState.value = AccountScreenState(isError = "Deposit failed :(")
         throw Exception()
      }
   }
}
