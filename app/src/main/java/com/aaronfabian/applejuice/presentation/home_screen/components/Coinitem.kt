package com.aaronfabian.applejuice.presentation.home_screen.components

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
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.utils.StringUtil
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun CoinItem(coinInfo: Coin, navController: NavController) {

   val coinName = coinInfo.name ?: "Er"
   val coinSymbol = coinInfo.symbol ?: "Er"
   val coinIconUrl = coinInfo.iconUrl ?: "Er"
   val coinPrice = coinInfo.price ?: 0
   val coinChange = coinInfo.change?.toFloat() ?: 0f
   val coinSparkLine = coinInfo.sparkline ?: emptyList()

   Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
         .height(80.dp)
         .fillMaxWidth()
         .clickable {

            val strCoinId = StringUtil.toPaprikaID(coinName, coinSymbol)

            println(strCoinId)
            navController.navigate(Screen.CoinDetailScreen.route + "/${strCoinId}")
         }
   ) {

      val format = coinIconUrl.split('.').last()
      if (format == "svg")
         LoadSVGImageIcon(url = coinIconUrl)
      else
         LoadGlideImage(url = coinIconUrl, contentDescription = "")

      Column(
         modifier = Modifier
            .weight(1.2f)
            .fillMaxHeight()
      ) {
         Text(
            text = coinSymbol,
            fontSize = 18.sp,
            color = mTextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 14.dp, start = 4.dp)
         )

         Text(
            text = coinName,
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 4.dp)
         )
      }

      Box(
         modifier = Modifier
            .weight(1.5f)
            .height(54.dp)
      ) {
         StockChart(
            infos = coinSparkLine,
            changeColor = coinChange
         )
      }

      Column(
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.End,
         modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
      ) {
         Text(
            text = "$${BigDecimal(coinPrice.toString()).setScale(2, RoundingMode.HALF_EVEN)}",
            color = mTextPrimary,
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp
         )
         Text(
            text = "$coinChange%",
            color = Color(if ((coinChange) > 0) 0xFFC1EAD2 else 0xFFF5ABAB),
            fontSize = 12.sp
         )
      }
   }
}

