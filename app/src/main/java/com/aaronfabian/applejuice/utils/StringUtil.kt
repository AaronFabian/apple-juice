package com.aaronfabian.applejuice.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object StringUtil {
   @RequiresApi(Build.VERSION_CODES.O)
   fun dateToReadableString(date: String): String {
      val formatter = DateTimeFormatter.ofPattern("yyyy MMM dd")
      val instant = Instant.parse(date)
      val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

      return formatter.format(localDateTime)
   }

   fun toFixDecimal(number: String): String {
      return "${BigDecimal(number).setScale(4, RoundingMode.HALF_EVEN)}"
   }

   fun toPaprikaID(coinName: String, coinSymbol: String): String {
      val strCoinId = StringBuilder()

      strCoinId.append(coinSymbol?.toLowerCase())
      strCoinId.append('-')
      strCoinId.append(
         coinName
            .toLowerCase()
            .replace(' ', '-')
      )

      return strCoinId.toString()
   }

   fun toReadAbleDateFromTimestamp(pattern: String, timestamp: Long): String {
      val sdf = SimpleDateFormat(pattern, Locale.getDefault())
      val date = Date(timestamp * 1000)
      return sdf.format(date)
   }
}