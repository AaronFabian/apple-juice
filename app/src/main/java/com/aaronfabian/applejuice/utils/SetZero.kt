package com.aaronfabian.applejuice.utils

import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.store.NavigationClass

fun setZero(cmp: NavigationClass) {
   cmp.setUser(User())
   cmp.setIsLoggedIn(false)
   FirebaseClass().signOutUser()
}