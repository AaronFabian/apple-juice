package com.aaronfabian.applejuice.utils

import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
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

   suspend fun updateUserName(uid: String, changedName: String) =
      suspendCancellableCoroutine<Boolean> { continuation ->
         val updateHashMap = HashMap<String, String>()
         updateHashMap[Constants.USER_NAME] = changedName

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

   suspend fun getCheckCoin(uid: String, coins: Coins) =
      suspendCancellableCoroutine<Map<String, Any>?> { continuation ->
         mFireStore
            .collection(Constants.COINS_COLLECTION)
            .document(uid)
            .collection(coins.coinId)
            .document(uid)
            .get()
            .addOnCompleteListener() { result ->
               continuation.resumeWith(Result.success(result.result.data))
            }
      }

   suspend fun getBoughtCoinAndTransaction(uid: String, coinId: String) =
      suspendCancellableCoroutine<List<DocumentSnapshot>> { continuation ->

         mFireStore
            .collection(Constants.COINS_COLLECTION)
            .whereEqualTo(Constants.OWNER_UID, uid)
            .whereEqualTo(Constants.COIN_ID, coinId)
            .get()
            .addOnSuccessListener { res ->
               // for (doc in res.documents) {
               // println(doc.id + "->" + doc.data)
               // }

               continuation.resumeWith(Result.success(res.documents))
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

   suspend fun updateDepositMoney(user: User, amount: Double) =
      suspendCancellableCoroutine<Boolean> { continuation ->
         val updateHashMap = HashMap<String, Double>()
         updateHashMap[Constants.USER_MONEY] = user.money + amount

         mFireStore
            .collection(Constants.USERS_COLLECTION)
            .document(user.uid)
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

//         mFireStore
//            .collection(Constants.COINS_COLLECTION)
//            .document(uid)
//            .collection(coins.coinId)
//            .get()
//            .addOnSuccessListener { res ->
//               for (document in res) {
//                  println("${document.id} -> ${document.data}")
//               }
//
//               continuation.resumeWith(Result.success(true))
//            }