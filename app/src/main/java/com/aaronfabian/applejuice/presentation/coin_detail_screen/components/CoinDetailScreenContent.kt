package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import android.os.Build
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.CoinDetail
import com.aaronfabian.applejuice.presentation.coin_detail_screen.CoinGraphState
import com.aaronfabian.applejuice.presentation.coin_detail_screen.CoinTickerState
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericErrorUI
import java.text.SimpleDateFormat
import java.util.*

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

   var allowTicker by remember {
      mutableStateOf(false)
   }

   var allowGraph by remember {
      mutableStateOf(false)
   }

   LaunchedEffect(key1 = _dataStateTicker, _coinGraphState) {
      if (_dataStateTicker.coin != null) allowTicker = true
      if (_coinGraphState.coin != null) allowGraph = true
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
                  GenericErrorUI(errorMessage = _coinGraphState.error)
               }
            }

            if (allowGraph) {
               CoinDetailCanvas(
                  _coinGraphState.coin!!,
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

            CoinDetailScreenNavbar(currentScreenState = coinDetailScreenState) {
               coinDetailScreenState = it
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

                     if (allowTicker) {
                        val modifierHashMap = HashMap<String, Modifier>()
                        modifierHashMap["imgCoinIconModifier"] = imgCoinIconModifier
                        modifierHashMap["coinPriceModifier"] = coinPriceModifier
                        modifierHashMap["rowButtonModifier"] = rowButtonModifier
                        modifierHashMap["textCoinPriceReportModifier"] = textCoinPriceReportModifier
                        modifierHashMap["containerPriceTickerModifier"] =
                           containerPriceTickerModifier

                        Price(
                           coinTicker = _dataStateTicker.coin!!,
                           coinLogo = dataState.logo,
                           modifierHashMap = modifierHashMap,
                           navController = navController
                        )
                     }

                     if (_dataStateTicker.error.isNotBlank()) {
                        PriceWhenError(errorMessage = _dataStateTicker.error)
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
