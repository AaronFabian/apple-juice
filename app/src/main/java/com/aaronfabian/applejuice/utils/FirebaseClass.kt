package com.aaronfabian.applejuice.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

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
}