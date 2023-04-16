package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aaronfabian.applejuice.domain.model.CoinDetail

@Composable
fun Overview(dataState: CoinDetail, modifierHashMap: HashMap<String, Modifier>) {
   Text(
      fontWeight = FontWeight.SemiBold,
      fontSize = 18.sp,
      text = "About ${dataState.name} (${dataState.symbol})",
      modifier = modifierHashMap["textTitleModifier"]!!
   )

   Text(
      text = dataState.description ?: "no data found",
      modifier = modifierHashMap["textDescriptionModifier"]!!
   )

   Text(
      text = "Founder",
      fontWeight = FontWeight.SemiBold,
      fontSize = 18.sp,
      modifier = modifierHashMap["textFounderDescModifier"]!!
   )


   var getName = "--"
   try {
      getName = dataState.team?.get(0)?.name.toString()
   } catch (e: Exception) {
      getName = "--"
   }

   Text(
      text = getName,
      modifier = modifierHashMap["textFounderNameModifier"]!!
   )

   modifierHashMap.clear()
}