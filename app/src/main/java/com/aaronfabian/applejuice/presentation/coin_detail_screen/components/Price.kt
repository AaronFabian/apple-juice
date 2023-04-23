package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.CoinTicker
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.utils.StringUtil
import com.aaronfabian.applejuice.utils.dataClass.TablePriceHelperData
import java.math.BigDecimal
import java.math.RoundingMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Price(coinTicker: CoinTicker, coinLogo: String, modifierHashMap: HashMap<String, Modifier>) {

   val toFixDecimalHelper = { price: Double ->
      BigDecimal(price)
         .setScale(
            2,
            RoundingMode.HALF_EVEN
         )
   }

   val imageLoader = ImageLoader
      .Builder(LocalContext.current)
      .componentRegistry { add(SvgDecoder(LocalContext.current)) }
      .build()


   val tablePriceDataClass = TablePriceHelperData.getTableData()
   val getPriceReportArr = ArrayList<String>()

   val coinPrice = coinTicker.quotes.USD.price // not include in array
   val volumeIn24h = coinTicker.quotes.USD.volume_24h
   val volumeChangeIn24h = coinTicker.quotes.USD.volume_24h_change_24h
   val marketCap = coinTicker.quotes.USD.market_cap.toDouble()
   val marketCapChangeIn24h = coinTicker.quotes.USD.market_cap_change_24h
   val percentChangeIn15m = coinTicker.quotes.USD.percent_change_15m
   val percentChangeIn30m = coinTicker.quotes.USD.percent_change_30m
   val percentChangeIn1h = coinTicker.quotes.USD.percent_change_1h
   val percentChangeIn6h = coinTicker.quotes.USD.percent_change_6h
   val percentChangeIn12h = coinTicker.quotes.USD.percent_change_12h
   val percentChangeIn24h = coinTicker.quotes.USD.percent_change_24h
   val percentChangeIn7d = coinTicker.quotes.USD.percent_change_7d
   val percentChangeIn30d = coinTicker.quotes.USD.percent_change_30d
   val percentChangeIn1y = coinTicker.quotes.USD.percent_change_1y
   val allTimeHighPrice = coinTicker.quotes.USD.ath_price
   val allTimeDate = coinTicker.quotes.USD.ath_date
   val percentFromAth = coinTicker.quotes.USD.percent_from_price_ath
   val totalSupply = coinTicker.total_supply
   val maxSupply = coinTicker.max_supply
   val circulatingCoin = coinTicker.circulating_supply

   getPriceReportArr.add("$\t${toFixDecimalHelper(volumeIn24h)}")
   getPriceReportArr.add("$volumeChangeIn24h")
   getPriceReportArr.add("$\t${toFixDecimalHelper(marketCap)}")
   getPriceReportArr.add("$marketCapChangeIn24h\t%")
   getPriceReportArr.add("$percentChangeIn15m\t%")
   getPriceReportArr.add("$percentChangeIn30m\t%")
   getPriceReportArr.add("$percentChangeIn1h\t%")
   getPriceReportArr.add("$percentChangeIn6h\t%")
   getPriceReportArr.add("$percentChangeIn12h\t%")
   getPriceReportArr.add("$percentChangeIn24h\t%")
   getPriceReportArr.add("$percentChangeIn7d\t%")
   getPriceReportArr.add("$percentChangeIn30d\t%")
   getPriceReportArr.add("$percentChangeIn1y\t%")
   getPriceReportArr.add("$\t${toFixDecimalHelper(allTimeHighPrice)}")
   getPriceReportArr.add(StringUtil.dateToReadableString(allTimeDate))
   getPriceReportArr.add("$percentFromAth\t%")
   getPriceReportArr.add("$totalSupply")
   getPriceReportArr.add("$maxSupply")
   getPriceReportArr.add("$circulatingCoin")

   CompositionLocalProvider(LocalImageLoader provides imageLoader) {
      val painter = rememberImagePainter(coinLogo)
      Image(
         painter = painter,
         contentDescription = "SVG image",
         contentScale = ContentScale.FillBounds,
         modifier = modifierHashMap["imgCoinIconModifier"]!!
      )
   }

   Text(
      text = "$${toFixDecimalHelper(coinPrice)}",
      fontSize = 18.sp,
      color = Color.LightGray,
      fontWeight = FontWeight.SemiBold,
      modifier = modifierHashMap["coinPriceModifier"]!!
   )

   Row(
      horizontalArrangement = Arrangement.SpaceAround,
      modifier = modifierHashMap["rowButtonModifier"]!!
   ) {
      IconButton(
         onClick = {
            println("buy")
         }, modifier = Modifier
            .background(Color.Green)
            .weight(.3f)
      ) {
         Row {
            Icon(
               painter = painterResource(id = R.drawable.ic_buy),
               contentDescription = "Icon buy"
            )
            Text(text = "Buy", fontSize = 16.sp, color = Color.White)
         }
      }

      IconButton(
         onClick = {
            println("sell")
         }, modifier = Modifier
            .background(Color.Red)
            .weight(.3f)
      ) {
         Row {
            Icon(
               painter = painterResource(id = R.drawable.ic_sell),
               contentDescription = "Icon Sell"
            )
            Text(text = "Sell", fontSize = 16.sp, color = Color.White)
         }
      }
   }

   Text(text = "coin price report", modifier = modifierHashMap["textCoinPriceReportModifier"]!!)

   Column(modifier = modifierHashMap["containerPriceTickerModifier"]!!) {

      tablePriceDataClass.forEachIndexed { index, it ->
         Row(
            modifier = Modifier
               .fillMaxWidth()
               .padding(top = 2.dp)
         ) {
            Text(
               fontSize = 18.sp,
               text = it.columnName,
               textAlign = TextAlign.Start,
               color = mPrimary,
               modifier = Modifier.weight(.5f)
            )
            Text(
               fontSize = 18.sp,
               textAlign = TextAlign.End,
               text = getPriceReportArr[index],
               color = Color.LightGray,
               modifier = Modifier.weight(.5f)
            )
         }
      }
   }
}