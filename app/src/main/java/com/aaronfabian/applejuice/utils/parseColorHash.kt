package com.aaronfabian.applejuice.utils

import android.util.Log
import androidx.compose.ui.graphics.Color

fun parseColorHash(colorHash: String): androidx.compose.ui.graphics.Color {
   return try {
      Color(android.graphics.Color.parseColor(colorHash))
   } catch (e: Exception) {
      Log.w("Color warning", e.message!!)
      Color(android.graphics.Color.parseColor(pickRandomColor()))
   }
}