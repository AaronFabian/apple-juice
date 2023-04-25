package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.CoinTicker
import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.DialogChildOnShouldSignIn
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent.CustomDialog
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent.DialogChildOnConfirmBuy
import com.aaronfabian.applejuice.store.NavigationComposition
import com.aaronfabian.applejuice.utils.FirebaseClass
import com.aaronfabian.applejuice.utils.StringUtil
import com.aaronfabian.applejuice.utils.dataClass.TablePriceHelperData
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Price(
   coinId: String,
   coinName: String,
   coinSymbol: String,
   coinTicker: CoinTicker,
   coinLogo: String,
   modifierHashMap: HashMap<String, Modifier>,
   navController: NavController,
) {
   val cmp = NavigationComposition.current
   val context = LocalContext.current
   val scope = rememberCoroutineScope()

   var isShowDialog by remember {
      mutableStateOf(false)
   }

   var buy = remember {
      mutableStateOf("Buy")
   }

   var isAllowedToBuy = remember {
      mutableStateOf(true)
   }

   val toFixDecimalHelper = { price: Double, scale: Int? ->
      BigDecimal(price)
         .setScale(
            scale ?: 2,
            RoundingMode.HALF_EVEN
         )
   }

   val imageLoader = ImageLoader
      .Builder(context)
      .componentRegistry { add(SvgDecoder(context)) }
      .build()

   val balance = cmp.user.money


   val tablePriceDataClass = TablePriceHelperData.getTableData()
   val getPriceReportArr = ArrayList<String>()

   val price = coinTicker.quotes.USD.price // not include in array
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

   getPriceReportArr.add("$\t${toFixDecimalHelper(volumeIn24h, null)}")
   getPriceReportArr.add("$volumeChangeIn24h")
   getPriceReportArr.add("$\t${toFixDecimalHelper(marketCap, null)}")
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
   getPriceReportArr.add("$\t${toFixDecimalHelper(allTimeHighPrice, null)}")
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
      text = "$${toFixDecimalHelper(price, null)}",
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
            isShowDialog = true
         }, modifier = Modifier
            .background(Color.Green)
            .weight(.3f)
      ) {
         Row {
            Icon(
               painter = painterResource(id = R.drawable.ic_buy),
               contentDescription = "Icon buy"
            )
            Text(text = buy.value, fontSize = 16.sp, color = Color.White)
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

   if (isShowDialog) {

      if (cmp.isLoggedIn)
         CustomDialog(
            onConfirm = { remainingBalance, coinAmount ->

               if (!isAllowedToBuy.value) return@CustomDialog

               isAllowedToBuy.value = false
               scope.launch {

                  try {
                     val currentTime = LocalDateTime.now()
                     val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                     val formattedTime = currentTime.format(formatter)


                     val coins = Coins(
                        coinId = coinId,
                        purchaseTime = formattedTime,
                        coinName = coinName,
                        ownerId = cmp.user.uid,
                        coinUri = coinLogo,
                        amount = coinAmount!!.toDouble()
                     )

                     val updateMoney =
                        FirebaseClass().updateUserMoney(remainingBalance!!, cmp.user.uid)
                     val postBoughtCoins = FirebaseClass().postPurchasedCoin(cmp.user.uid, coins)
                     if (updateMoney && postBoughtCoins) {
                        cmp.user.money = remainingBalance.toDouble()
                        isShowDialog = false
                        Toast.makeText(
                           context,
                           "Purchase complete ! Thank you for your purchase.",
                           Toast.LENGTH_LONG
                        ).show()
                     } else
                        Toast.makeText(
                           context,
                           "Something wrong ! Please try again later.",
                           Toast.LENGTH_LONG
                        ).show()
                  } catch (e: Exception) {
                     e.printStackTrace()
                     Toast.makeText(
                        context,
                        "Something wrong :( Please try again later.",
                        Toast.LENGTH_LONG
                     ).show()
                  }
                  
                  isAllowedToBuy.value = true
               }
            },
            onDismiss = {
               isShowDialog = false
            },
            text1 = coinName,
            text2 = coinSymbol,
            text3 = toFixDecimalHelper(price, 5).toString(),
            iconId = coinLogo,
            children = { onDis, onCon, text1, text2, text3, _ ->
               DialogChildOnConfirmBuy(
                  onConfirm = onCon,
                  onDismiss = onDis,
                  coinName = text1,
                  coinSymbol = text2,
                  price = text3,
               )
            }
         )
      else
         CustomDialog(
            onDismiss = {
               isShowDialog = false
            },
            onConfirm = { _, _ ->
               navController.navigate(Screen.SignInScreen.route)
            },
            children = { onDis, onCon, _, _, _, _ ->
               DialogChildOnShouldSignIn(onConfirm = { onCon(null, null) }, onDismiss = { onDis() })
            }
         )
   }
}