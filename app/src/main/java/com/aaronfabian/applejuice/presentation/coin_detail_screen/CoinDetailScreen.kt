package com.aaronfabian.applejuice.presentation.coin_detail_screen

import android.os.Build
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.CoinDetail
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.CoinDetailCanvas
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.Overview
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.Related
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.VerticalDivider
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.utils.StringUtil
import com.aaronfabian.applejuice.utils.dataClass.TablePriceHelperData
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinDetailScreen(
   navController: NavController,
   viewModel: CoinDetailViewModel = hiltViewModel()
) {

   val state = viewModel.state.value
   val state2 = viewModel.state2.value
   val state3 = viewModel.state3.value

   if (state.coin != null) {
      CoinDetailScreenContent(state.coin, state2, state3, navController)
   }


   if (state.error.isNotBlank()) {
      Box(modifier = Modifier.fillMaxSize()) {
         Text(
            text = state.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp)
         )
      }
   }

   if (state.isLoading) {
      Box(modifier = Modifier.fillMaxSize()) {
         CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
   }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinDetailScreenContent(
   dataState: CoinDetail,
   _dataStateTicker: CoinTickerState,
   _coinGraphState: CoinGraphState,
   navController: NavController,
) {
   var coinDetailScreenState by remember {
      mutableStateOf("price")
   }

   val inputTime = dataState.started_at
   var formattedTime = "--:--"
   if (!inputTime.isNullOrEmpty()) {
      val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
      format.timeZone = TimeZone.getTimeZone("UTC")
      val parsedTime = format.parse(inputTime)
      formattedTime =
         SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(parsedTime)
   }


   Column(modifier = Modifier.fillMaxWidth()) {
      ConstraintLayout(
         modifier = Modifier
            .fillMaxWidth()
      ) {
         // toolbar
         val (toolbarRef, _) = createRefs()
         val (dividerRef, _) = createRefs()
         val (canvasRef, _) = createRefs()
         val (miniToolbarInfo, _) = createRefs()
         val (navbarCoinDetailScreen, _) = createRefs()
         val (screenCoinDetailState, _) = createRefs()


         Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
               .height(56.dp)
               .fillMaxWidth()
               .constrainAs(toolbarRef) {
                  top.linkTo(parent.top)
                  start.linkTo(parent.start)
               }) {

            val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
            IconButton(
               modifier = Modifier.padding(start = 16.dp, end = 16.dp),
               onClick = { dispatcher.onBackPressed() }) {
               Icon(
                  tint = Color.LightGray,
                  painter = painterResource(id = R.drawable.ic_back_btn),
                  contentDescription = "Back Icon Button",
                  modifier = Modifier.size(26.dp)
               )
            }

            Text(
               text = dataState.name ?: "Err",
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold,
               color = mTextPrimary
            )

            IconButton(
               modifier = Modifier.padding(start = 16.dp, end = 16.dp),
               onClick = {
                  // TODO: implement bookmark navigation
               }) {
               Icon(
                  tint = Color.LightGray,
                  painter = painterResource(id = R.drawable.ic_bookmark),
                  contentDescription = "Back Icon Button",
                  modifier = Modifier.size(26.dp)
               )
            }
         }

         Divider(
            thickness = .8.dp,
            color = Color.LightGray,
            modifier = Modifier
               .fillMaxWidth()
               .constrainAs(dividerRef) {
                  top.linkTo(toolbarRef.bottom)
                  start.linkTo(parent.start)
               })

         Box(
            modifier = Modifier
               .fillMaxWidth()
               .height(260.dp)
               .background(Color(0xFFF1F1F1))
               .constrainAs(canvasRef) {
                  top.linkTo(dividerRef.bottom)
                  start.linkTo(parent.start)
               }) {

            if (_coinGraphState.isLoading) {
               Box(modifier = Modifier.fillMaxSize()) {
                  CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
               }
            }

            if (_coinGraphState.error.isNotBlank()) {
               val responseCode = _coinGraphState.error.split(' ')[1]

               if (responseCode == "429") {
                  Box(
                     modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.onError)
                  ) {
                     Text(
                        text = "can't display chart for the moment.",
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 20.dp)
                     )
                  }
               } else {
                  Box(modifier = Modifier.fillMaxSize()) {
                     Text(
                        text = _coinGraphState.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 20.dp)
                     )
                  }
               }
            }

            if (_coinGraphState.coin != null) {
               CoinDetailCanvas(
                  _coinGraphState.coin,
                  modifier = Modifier
                     .background(MaterialTheme.colors.onError)
                     .fillMaxSize()
                     .padding(end = 8.dp)
               )
            }

         }

         // start middle card
         Box(
            modifier = Modifier
               .fillMaxWidth()
               .offset(y = 6.dp)
               .padding(start = 20.dp, end = 20.dp)
               .constrainAs(miniToolbarInfo) {
                  top.linkTo(canvasRef.bottom)
                  start.linkTo(parent.start)
               }) {
            Card(
               shape = MaterialTheme.shapes.medium,
               modifier = Modifier
                  .fillMaxWidth()
                  .height(70.dp)
            ) {
               Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.fillMaxSize()
               ) {
                  Column(
                     verticalArrangement = Arrangement.SpaceEvenly,
                     modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 8.dp)
                  ) {

                     Text(
                        fontSize = 12.sp,
                        text = "Start at",
                        fontWeight = FontWeight.Thin,
                        color = mTextPrimary
                     )
                     Text(
                        fontSize = 14.sp,
                        text = formattedTime,
                        fontWeight = FontWeight.Normal,
                        color = mTextPrimary
                     )
                  }

                  VerticalDivider()

                  Column(
                     verticalArrangement = Arrangement.SpaceEvenly,
                     modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 16.dp)
                  ) {
                     Text(
                        fontSize = 12.sp,
                        text = "Rank",
                        fontWeight = FontWeight.Thin,
                        color = mTextPrimary
                     )
                     Text(
                        fontSize = 16.sp,
                        text = "${dataState.rank}",
                        fontWeight = FontWeight.Normal,
                        color = mTextPrimary
                     )
                  }

                  VerticalDivider()

                  Column(
                     verticalArrangement = Arrangement.SpaceEvenly,
                     modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 16.dp)
                  ) {
                     Text(
                        fontSize = 12.sp,
                        text = "Is active",
                        fontWeight = FontWeight.Thin,
                        color = mTextPrimary
                     )

                     val statusActive = dataState.is_active == true
                     Text(
                        fontSize = 16.sp,
                        text = if (statusActive) "Yes" else "No",
                        fontWeight = FontWeight.Normal,
                        color = if (statusActive) Color.Green else Color.Red
                     )
                  }
               }
            }
         } // end middle Card


         Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
               .padding(start = 18.dp, end = 18.dp, top = 34.dp)
               .height(40.dp)
               .fillMaxWidth()
               .constrainAs(navbarCoinDetailScreen) {
                  top.linkTo(miniToolbarInfo.bottom)
                  start.linkTo(parent.start)
               }
         ) {

            val navbarTextArr = LinkedHashMap<String, String>()
            navbarTextArr["price"] = "Price"
            navbarTextArr["related"] = "Related"
            navbarTextArr["overview"] = "Overview"

            navbarTextArr.forEach { it ->
               Column(
                  verticalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier
                     .clickable { coinDetailScreenState = it.key }
                     .width(intrinsicSize = IntrinsicSize.Min)
                     .fillMaxHeight()
               ) {
                  Text(
                     text = it.value,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold,
                     color = if (it.key == coinDetailScreenState) mPrimary else mTextPrimary
                  )

                  if (it.key == coinDetailScreenState)
                     Divider(thickness = 3.dp, color = mPrimary)
               }
            }
         } // navbar coin detail screen


         // start coin detail screen navigation
         Box(
            modifier = Modifier
               .constrainAs(screenCoinDetailState) {
                  top.linkTo(navbarCoinDetailScreen.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
               .height(310.dp)
               .verticalScroll(rememberScrollState())
         ) {
            ConstraintLayout(
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(start = 18.dp, end = 18.dp, top = 10.dp)
            ) {

               // Overview Ref
               val textTitle = createRef()
               val textDescription = createRef()
               val textFounder = createRef()
               val textFounderName = createRef()
               val textTag = createRef()
               val flowRowTag = createRef()

               val textTitleModifier = Modifier
                  .constrainAs(textTitle) {
                     top.linkTo(parent.top)
                     start.linkTo(parent.start)
                  }

               val textDescriptionModifier = Modifier
                  .padding(top = 8.dp)
                  .constrainAs(textDescription) {
                     top.linkTo(textTitle.bottom)
                     start.linkTo(parent.start)
                  }

               val textFounderDescModifier = Modifier
                  .padding(top = 16.dp)
                  .height(20.dp)
                  .constrainAs(textFounder) {
                     top.linkTo(textDescription.bottom)
                     start.linkTo(parent.start)
                  }

               val textFounderNameModifier = Modifier
                  .padding(top = 46.dp, bottom = 16.dp)
                  .constrainAs(textFounderName) {
                     top.linkTo(textDescription.bottom)
                     start.linkTo(parent.start)
                  }

               // Requirements Ref
               val containerTeam = createRef()

               val containerTeamModifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 16.dp)
                  .constrainAs(containerTeam) {
                     top.linkTo(textTitle.bottom)
                     start.linkTo(parent.start)
                  }

               val textTagModifier = Modifier
                  .padding(top = 16.dp)
                  .constrainAs(textTag) {
                     top.linkTo(containerTeam.bottom)
                     start.linkTo(parent.start)
                  }

               val flowRowTagsModifier = Modifier
                  .padding(top = 16.dp, bottom = 8.dp)
                  .constrainAs(flowRowTag) {
                     top.linkTo(textTag.bottom)
                     start.linkTo(parent.start)
                  }

               // Price Ref
               val coinPriceRef = createRef()
               val textCoinPriceReportRef = createRef()
               val containerPriceTicker = createRef()
               val rowButtonRef = createRef()

               val imgCoinIconModifier = Modifier
                  .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                  .size(46.dp)
                  .clip(CircleShape)
                  .constrainAs(textTitle) {
                     top.linkTo(parent.top)
                     start.linkTo(parent.start)
                     end.linkTo(parent.end)
                  }

               val coinPriceModifier = Modifier
                  .padding(top = 8.dp)
                  .constrainAs(coinPriceRef) {
                     top.linkTo(textTitle.bottom)
                     start.linkTo(parent.start)
                     end.linkTo(parent.end)
                  }

               val rowButtonModifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp, bottom = 8.dp)
                  .constrainAs(rowButtonRef) {
                     top.linkTo(coinPriceRef.bottom)
                     start.linkTo(parent.start)
                  }

               val textCoinPriceReportModifier = Modifier
                  .padding(top = 8.dp, bottom = 8.dp)
                  .constrainAs(textCoinPriceReportRef) {
                     top.linkTo(rowButtonRef.bottom)
                     start.linkTo(parent.start)
                     end.linkTo(parent.end)
                  }

               val containerPriceTickerModifier = Modifier
                  .fillMaxWidth()
                  .height(intrinsicSize = IntrinsicSize.Min)
                  .padding(start = 18.dp, end = 18.dp)
                  .constrainAs(containerPriceTicker) {
                     top.linkTo(textCoinPriceReportRef.bottom)
                     start.linkTo(parent.start)
                  }

               when (coinDetailScreenState) {
                  "price" -> {

                     val modifierHashMap = HashMap<String, Modifier>()
                     modifierHashMap["imgCoinIconModifier"] = imgCoinIconModifier

                     val imageLoader = ImageLoader
                        .Builder(LocalContext.current)
                        .componentRegistry { add(SvgDecoder(LocalContext.current)) }
                        .build()

                     if (_dataStateTicker.coin != null) {

                        val tickerData = _dataStateTicker.coin

                        val toFixDecimalHelper = { price: Double ->
                           BigDecimal(price)
                              .setScale(
                                 2,
                                 RoundingMode.HALF_EVEN
                              )
                        }

                        val tablePriceDataClass = TablePriceHelperData.getTableData()
                        val getPriceReportArr = ArrayList<String>()

                        val coinPrice = tickerData.quotes.USD.price // not include in array
                        val volumeIn24h = tickerData.quotes.USD.volume_24h
                        val volumeChangeIn24h = tickerData.quotes.USD.volume_24h_change_24h
                        val marketCap = tickerData.quotes.USD.market_cap.toDouble()
                        val marketCapChangeIn24h = tickerData.quotes.USD.market_cap_change_24h
                        val percentChangeIn15m = tickerData.quotes.USD.percent_change_15m
                        val percentChangeIn30m = tickerData.quotes.USD.percent_change_30m
                        val percentChangeIn1h = tickerData.quotes.USD.percent_change_1h
                        val percentChangeIn6h = tickerData.quotes.USD.percent_change_6h
                        val percentChangeIn12h = tickerData.quotes.USD.percent_change_12h
                        val percentChangeIn24h = tickerData.quotes.USD.percent_change_24h
                        val percentChangeIn7d = tickerData.quotes.USD.percent_change_7d
                        val percentChangeIn30d = tickerData.quotes.USD.percent_change_30d
                        val percentChangeIn1y = tickerData.quotes.USD.percent_change_1y
                        val allTimeHighPrice = tickerData.quotes.USD.ath_price
                        val allTimeDate = tickerData.quotes.USD.ath_date
                        val percentFromAth = tickerData.quotes.USD.percent_from_price_ath

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


                        CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                           val painter = rememberImagePainter(dataState.logo)
                           Image(
                              painter = painter,
                              contentDescription = "SVG image",
                              contentScale = ContentScale.FillBounds,
                              modifier = imgCoinIconModifier
                           )
                        }

                        Text(
                           text = "$${toFixDecimalHelper(coinPrice)}",
                           fontSize = 18.sp,
                           color = Color.LightGray,
                           fontWeight = FontWeight.SemiBold,
                           modifier = coinPriceModifier
                        )

                        Row(
                           horizontalArrangement = Arrangement.SpaceAround,
                           modifier = rowButtonModifier
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

                        Text(text = "coin price report", modifier = textCoinPriceReportModifier)

                        Column(modifier = containerPriceTickerModifier) {

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

                     if (_dataStateTicker.error.isNotBlank()) {

                        val responseCodeTicker = _dataStateTicker.error.split(' ')[1]


                        if (responseCodeTicker == "404") {
                           Box(modifier = Modifier.fillMaxSize()) {
                              Text(
                                 text = "data is missing",
                                 color = MaterialTheme.colors.error,
                                 textAlign = TextAlign.Center,
                                 modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                              )
                           }
                        } else {

                           Box(modifier = Modifier.fillMaxSize()) {
                              Text(
                                 text = _dataStateTicker.error,
                                 color = MaterialTheme.colors.error,
                                 textAlign = TextAlign.Center,
                                 modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                              )
                           }

                        }
                     }

                     if (_dataStateTicker.isLoading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                           CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                     }


                  }
                  "overview" -> {

                     val modifierHashMap = HashMap<String, Modifier>()
                     modifierHashMap["textTitleModifier"] = textTitleModifier
                     modifierHashMap["textDescriptionModifier"] = textDescriptionModifier
                     modifierHashMap["textFounderDescModifier"] = textFounderDescModifier
                     modifierHashMap["textFounderNameModifier"] = textFounderNameModifier

                     Overview(dataState = dataState, modifierHashMap = modifierHashMap)
                  }
                  "related" -> {

                     val modifierHashMap = HashMap<String, Modifier>()
                     modifierHashMap["textTitleModifier"] = textTitleModifier
                     modifierHashMap["containerTeamModifier"] = containerTeamModifier
                     modifierHashMap["textTagModifier"] = textTagModifier
                     modifierHashMap["flowRowTagsModifier"] = flowRowTagsModifier

                     Related(
                        modifier = modifierHashMap,
                        navController = navController,
                        team = dataState.team,
                        tags = dataState.tags
                     )
                  }
               }
            }
         }
      }
   }
}
