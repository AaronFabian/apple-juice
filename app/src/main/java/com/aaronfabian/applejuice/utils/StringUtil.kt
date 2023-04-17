package com.aaronfabian.applejuice.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object StringUtil {
   @RequiresApi(Build.VERSION_CODES.O)
   fun dateToReadableString(date: String): String {
      val formatter = DateTimeFormatter.ofPattern("yyyy MMM dd")
      val instant = Instant.parse(date)
      val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

      return formatter.format(localDateTime)
   }
}