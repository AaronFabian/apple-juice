package com.aaronfabian.applejuice.presentation.my_coin_detail_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.CoinDetailCanvas
import com.aaronfabian.applejuice.presentation.my_coin_detail_screen.MyCoinDetailScreenViewModel
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.utils.StringUtil
import com.aaronfabian.applejuice.utils.parseColorHash
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoinInformation(
   viewModel: MyCoinDetailScreenViewModel,
   navController: NavController
) {
   val coinName = viewModel.coinInfoHashMap.value["name"].toString()
   val amount = viewModel.coinInfoHashMap.value["amount"].toString().toDouble()
   val value = viewModel.coinInfoHashMap.value["value"].toString().toDouble()
   val coinUri = viewModel.coinInfoHashMap.value["coinUri"].toString()
   val coinColor = viewModel.coinInfoHashMap.value["color"].toString()
   val transactionHistory = viewModel.coinInfoHashMap.value["list"] as List<*>

   // loading warning !
   val oneCoinState = viewModel.state.value
   var average: String? = null
   var capitalGain: String? = null
   var currentCoinPrice: String? = null
   var changeIn24h: String? = null
   var volumeIn24h: String? = null
   var btcPrice: String? = null
   var sparkline: List<String?>? = null

   if (oneCoinState.oneCoin != null) {
      average = (value / amount).toString()
      currentCoinPrice = oneCoinState.oneCoin.data.coins[0].price.toString()
      capitalGain =
         ((currentCoinPrice.toDouble() - average.toDouble()) / average.toDouble() * 100).toString()
      changeIn24h = oneCoinState.oneCoin.data.coins[0].change.toString()
      volumeIn24h = oneCoinState.oneCoin.data.coins[0].volume24h.toString()
      btcPrice = oneCoinState.oneCoin.data.coins[0].btcPrice.toString()
      sparkline = oneCoinState.oneCoin.data.coins[0].sparkline
   }

   ConstraintLayout(
      modifier = Modifier
         .fillMaxWidth()
         .padding(start = 8.dp, end = 8.dp)
   ) {
      val topSpacerRef = createRef()
      val glideImageRef = createRef()
      val textCoinName = createRef()
      val textHoldingAmount = createRef()
      val textCoinTotalAmount = createRef()
      val spacerWalletPortfolio = createRef()
      val textWalletPortfolio = createRef()
      val textAverageLabel = createRef()
      val textMyCoinValue = createRef()
      val textMyCapitalGainInfo = createRef()
      val textTotalValueLabel = createRef()
      val textMyCoinAverage = createRef()
      val textMyCoinTotalValue = createRef()

      val spacerCurrentCoinInformation = createRef()
      val textCoinInformation = createRef()
      val textCurrentPriceLabel = createRef()
      val textCurrentPriceInfo = createRef()
      val textChange24hLabel = createRef()
      val textChange24hInfo = createRef()
      val textVolume24hLabel = createRef()
      val textVolume24hInfo = createRef()
      val textBtcPriceLabel = createRef()
      val textBtcPriceInfo = createRef()
      val spacerGraphCanvas = createRef()
      val boxContainerCanvasGraph = createRef()
      val spacerLazyColumnTransactionHistory = createRef()
      val lazyColumnTransactionHistory = createRef()

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .constrainAs(topSpacerRef) {
               top.linkTo(parent.top)
               start.linkTo(parent.start)
            }
      )

      GlideImage(
         model = coinUri,
         contentDescription = "Glide image to display coin logo",
         modifier =
         Modifier
            .padding(start = 16.dp)
            .size(40.dp)
            .constrainAs(glideImageRef) {
               top.linkTo(topSpacerRef.bottom)
               start.linkTo(parent.start)
            })


      Text(
         text = coinName,
         color = mTextPrimary,
         fontSize = 18.sp,
         modifier = Modifier
            .padding(start = 16.dp)
            .constrainAs(textCoinName) {
               top.linkTo(glideImageRef.top)
               start.linkTo(glideImageRef.end)
            })


      Text(
         text = "Holding amount",
         color = Color.Gray,
         fontSize = 16.sp,
         modifier = Modifier
            .padding(start = 16.dp)
            .constrainAs(textHoldingAmount) {
               top.linkTo(textCoinName.bottom)
               start.linkTo(glideImageRef.end)
            }
      )

      Text(
         text = amount.toString(),
         color = Color.Gray,
         modifier = Modifier
            .padding(end = 16.dp)
            .constrainAs(textCoinTotalAmount) {
               top.linkTo(textHoldingAmount.top)
               end.linkTo(parent.end)
            }
      )

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .constrainAs(spacerWalletPortfolio) {
               top.linkTo(glideImageRef.bottom)
               start.linkTo(parent.start)
            }
      )

      Text(
         text = "Wallet portfolio",
         color = mTextPrimary,
         fontSize = 18.sp,
         fontStyle = FontStyle.Italic,
         modifier = Modifier
            .padding(start = 16.dp)
            .constrainAs(textWalletPortfolio) {
               top.linkTo(spacerWalletPortfolio.bottom)
               start.linkTo(spacerWalletPortfolio.start)
            }
      )

      Text(
         text = "Average",
         color = Color.Gray,
         modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
            .constrainAs(textAverageLabel) {
               top.linkTo(textWalletPortfolio.bottom)
               start.linkTo(textWalletPortfolio.start)
            }
      )

      Text(
         text = "Capital gain",
         color = Color.Gray,
         modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
            .constrainAs(textMyCoinValue) {
               top.linkTo(textAverageLabel.bottom)
               start.linkTo(textWalletPortfolio.start)
            }
      )

      Text(
         text = "Total value",
         color = Color.Gray,
         modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
            .constrainAs(textTotalValueLabel) {
               top.linkTo(textMyCoinValue.bottom)
               start.linkTo(textWalletPortfolio.start)
            }
      )

      if (oneCoinState.isLoading) {
         Text(
            text = "please wait...",
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCoinAverage) {
                  top.linkTo(textAverageLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            text = "please wait...",
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCapitalGainInfo) {
                  top.linkTo(textMyCoinValue.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            text = "please wait...",
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCoinTotalValue) {
                  top.linkTo(textMyCoinAverage.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )
      }

      if (oneCoinState.oneCoin != null) {
         Text(
            text = average!!,
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCoinAverage) {
                  top.linkTo(textAverageLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            color = if (capitalGain!!.toDouble() > 0) Color.Green else Color.Red,
            text = "$capitalGain%",
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCapitalGainInfo) {
                  top.linkTo(textMyCoinValue.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            text = value.toString(),
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textMyCoinTotalValue) {
                  top.linkTo(textTotalValueLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(30.dp)
               .padding(start = 16.dp)
               .constrainAs(spacerCurrentCoinInformation) {
                  top.linkTo(textTotalValueLabel.bottom)
                  start.linkTo(textWalletPortfolio.start)
               })

         Text(
            text = "Coin Information",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
               .padding(start = 16.dp, top = 8.dp)
               .constrainAs(textCoinInformation) {
                  top.linkTo(spacerCurrentCoinInformation.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         Text(
            text = "Current price",
            color = Color.Gray,
            modifier = Modifier
               .padding(start = 16.dp, top = 8.dp)
               .constrainAs(textCurrentPriceLabel) {
                  top.linkTo(textCoinInformation.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )


         Text(
            text = currentCoinPrice.toString(),
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textCurrentPriceInfo) {
                  top.linkTo(textCoinInformation.bottom)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )


         Text(
            text = "Change in 24h",
            color = Color.Gray,
            modifier = Modifier
               .padding(start = 16.dp, top = 8.dp)
               .constrainAs(textChange24hLabel) {
                  top.linkTo(textCurrentPriceInfo.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         Text(
            text = changeIn24h!!,
            color = if (changeIn24h.toDouble() > 0) Color.Green else Color.Red,
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textChange24hInfo) {
                  top.linkTo(textChange24hLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            text = "Volume in 24h (USD)",
            color = Color.Gray,
            modifier = Modifier
               .padding(start = 16.dp, top = 8.dp)
               .constrainAs(textVolume24hLabel) {
                  top.linkTo(textChange24hLabel.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         Text(
            text = volumeIn24h!!,
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textVolume24hInfo) {
                  top.linkTo(textVolume24hLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Text(
            text = "Btc price",
            color = Color.Gray,
            modifier = Modifier
               .padding(start = 16.dp, top = 8.dp)
               .constrainAs(textBtcPriceLabel) {
                  top.linkTo(textVolume24hLabel.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         Text(
            text = btcPrice!!,
            modifier = Modifier
               .padding(end = 16.dp, top = 8.dp)
               .constrainAs(textBtcPriceInfo) {
                  top.linkTo(textBtcPriceLabel.top)
                  end.linkTo(textCoinTotalAmount.end)
               }
         )

         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(16.dp)
               .constrainAs(spacerGraphCanvas) {
                  top.linkTo(textBtcPriceLabel.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         CoinDetailCanvas(
            coinGraphData = sparkline,
            modifier = Modifier
               .fillMaxWidth()
               .height(160.dp)
               .padding(start = 8.dp, end = 8.dp)
               .constrainAs(boxContainerCanvasGraph) {
                  top.linkTo(spacerGraphCanvas.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(28.dp)
               .constrainAs(spacerLazyColumnTransactionHistory) {
                  top.linkTo(boxContainerCanvasGraph.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         )

         LazyColumn(
            modifier = Modifier
               .height(180.dp)
               .padding(start = 8.dp, end = 8.dp)
               .constrainAs(lazyColumnTransactionHistory) {
                  top.linkTo(spacerLazyColumnTransactionHistory.bottom)
                  start.linkTo(textWalletPortfolio.start)
               }
         ) {
            items(transactionHistory) { item ->
               val coins = item as Coins

               ConstraintLayout(
                  modifier = Modifier
                     .fillMaxWidth()
                     .height(65.dp)
               ) {
                  val textTransactionTimeRef = createRef()
                  val textAmountBought = createRef()
                  val textAmountBoughtInfo = createRef()
                  val textTotalBoughtValueLabel = createRef()
                  val textTotalBoughtValue = createRef()
                  val dividerColumn = createRef()

                  Text(
                     text = coins.purchaseTime,
                     fontSize = 18.sp,
                     modifier = Modifier
                        .constrainAs(textTransactionTimeRef) {
                           top.linkTo(parent.top)
                           start.linkTo(parent.start)
                           bottom.linkTo(parent.bottom)
                        }
                  )

                  Text(
                     text = "Amount :",
                     fontStyle = FontStyle.Italic,
                     fontSize = 12.sp,
                     color = Color.LightGray,
                     modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(textAmountBought) {
                           bottom.linkTo(textAmountBoughtInfo.bottom)
                           end.linkTo(textAmountBoughtInfo.start)
                        }
                  )

                  Text(
                     text = "${coins.amount}",
                     fontSize = 16.sp,
                     color = mTextPrimary,
                     modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(textAmountBoughtInfo) {
                           top.linkTo(parent.top)
                           end.linkTo(parent.end)
                        }
                  )


                  Text(
                     text = "Total :",
                     fontStyle = FontStyle.Italic,
                     fontSize = 12.sp,
                     color = Color.LightGray,
                     modifier = Modifier
                        .padding(end = 16.dp)
                        .constrainAs(textTotalBoughtValueLabel) {
                           bottom.linkTo(textTotalBoughtValue.bottom)
                           end.linkTo(textTotalBoughtValue.start)
                        }
                  )

                  Text(
                     text = StringUtil.toFixDecimal(coins.coinValue.toString()),
                     fontSize = 16.sp,
                     color = mTextPrimary,
                     modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(textTotalBoughtValue) {
                           top.linkTo(textAmountBoughtInfo.bottom)
                           bottom.linkTo(parent.bottom)
                           end.linkTo(parent.end)
                        }
                  )

                  Divider(
                     thickness = 0.6.dp,
                     color = parseColorHash(coinColor),
                     modifier = Modifier.constrainAs(dividerColumn) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                     }
                  )
               }
            }
         }
      }
   }
}