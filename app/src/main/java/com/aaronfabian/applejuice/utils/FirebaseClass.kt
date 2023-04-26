package com.aaronfabian.applejuice.utils

import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class FirebaseClass {

   private val mFireStore = FirebaseFirestore.getInstance()

   fun postRegisteredUser(uid: String, user: User) {

      // val onSuccessFn = { _: Void -> println("success") }
      val onFailureFn = { e: Exception -> e.printStackTrace() }

      mFireStore
         .collection(Constants.USERS_COLLECTION)
         .document(uid)
         .set(user, SetOptions.merge())
         .addOnFailureListener(onFailureFn)
   }

   fun signOutUser() {
      FirebaseAuth.getInstance().signOut()
   }

   suspend fun updateUserMoney(remainingBalance: String, uid: String) =
      suspendCancellableCoroutine<Boolean> { continuation ->
         val updateHashMap = HashMap<String, Double>()
         updateHashMap[Constants.USER_MONEY] = remainingBalance.toDouble()

         mFireStore
            .collection(Constants.USERS_COLLECTION)
            .document(uid)
            .update(updateHashMap as Map<String, Any>)
            .addOnSuccessListener {
               continuation.resumeWith(Result.success(true))
            }
            .addOnFailureListener { exception ->
               continuation.resumeWithException(exception)
            }
            .addOnCanceledListener {
               continuation.cancel()
            }
      }

   suspend fun postPurchasedCoin(uid: String, coins: Coins) =
      suspendCancellableCoroutine<Boolean> { continuation ->

         mFireStore
            .collection(Constants.COINS_COLLECTION)
            .document()
            .set(coins, SetOptions.merge())
            .addOnSuccessListener {
               continuation.resumeWith(Result.success(true))
            }
            .addOnFailureListener { exception ->
               continuation.resumeWithException(exception)
            }
            .addOnCanceledListener {
               continuation.cancel()
            }
      }

   suspend fun getUserCoin(uid: String) =
      suspendCancellableCoroutine<QuerySnapshot> { continuation ->

         mFireStore
            .collection(Constants.COINS_COLLECTION)
            .whereEqualTo(Constants.OWNER_UID, uid)
            .get()
            .addOnCompleteListener {
               continuation.resumeWith(Result.success(it.result))
            }
            .addOnFailureListener { exception ->
               continuation.resumeWithException(exception)
            }
            .addOnCanceledListener {
               continuation.cancel()
            }
      }
}