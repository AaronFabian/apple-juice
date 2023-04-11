package com.aaronfabian.applejuice.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aaronfabian.applejuice.data.remote.dto.Coin
import com.aaronfabian.applejuice.presentation.Screen
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun CoinItem(coinInfo: Coin, navController: NavController) {
   Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
         .clickable {
            val stringBuilder = StringBuilder()

            val getCoinSymbol = coinInfo.symbol
            val getCoinName = coinInfo.name

            stringBuilder.append(getCoinSymbol.toLowerCase())
            stringBuilder.append('-')
            stringBuilder.append(getCoinName.toLowerCase())

            println(stringBuilder)

            navController.navigate(Screen.CoinDetailScreen.route + "/${stringBuilder}")
         }
         .height(80.dp)
         .fillMaxWidth()
   ) {
      LoadSVGImageIcon(url = coinInfo.iconUrl)

      Column(
         modifier = Modifier
            .weight(.8f)
            .fillMaxHeight()
      ) {
         Text(
            text = coinInfo.symbol,
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 14.dp, start = 4.dp)
         )

         Text(
            text = coinInfo.name,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 4.dp)
         )
      }

      Column(
         modifier = Modifier
            .weight(1.5f)
            .fillMaxHeight()
            .background(Color.Blue)
      ) {
         // TODO: implement graphic

      }

      Column(
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.End,
         modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
      ) {

         Text(
            text = "$${BigDecimal(coinInfo.price).setScale(2, RoundingMode.HALF_EVEN)}",
            color = Color.DarkGray,
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp
         )
         Text(
            text = coinInfo.change + "%",
            color = Color(if (coinInfo.change.toFloat() > 0) 0xFFC1EAD2 else 0xFFF5ABAB),
            fontSize = 12.sp
         )
      }
   }
}