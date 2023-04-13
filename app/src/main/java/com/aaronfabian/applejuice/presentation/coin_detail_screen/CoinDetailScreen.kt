package com.aaronfabian.applejuice.presentation.coin_detail_screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.CoinDetail
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.Overview
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.PeopleItem
import com.aaronfabian.applejuice.presentation.coin_detail_screen.components.VerticalDivider
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.google.accompanist.flowlayout.FlowRow
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CoinDetailScreen(
   navController: NavController,
   viewModel: CoinDetailViewModel = hiltViewModel()
) {

   val state = viewModel.state.value

   if (state.coin != null) {
      CoinDetailScreenContent(state.coin, navController)
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

@Composable
fun CoinDetailScreenContent(dataState: CoinDetail, navController: NavController) {

   var coinDetailScreenState by remember {
      mutableStateOf("overview")
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
               color = Color.DarkGray
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
            Text(text = "Todo implement canvas", modifier = Modifier.align(Alignment.Center))
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
                        color = Color(0xFF000000)
                     )
                     Text(
                        fontSize = 14.sp,
                        text = formattedTime,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
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
                        color = Color(0xFF000000)
                     )
                     Text(
                        fontSize = 16.sp,
                        text = "${dataState.rank}",
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
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
                        color = Color(0xFF000000)
                     )
                     Text(
                        fontSize = 16.sp,
                        text = "Yes",
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
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

            val navbarTextArr = HashMap<String, String>()
            navbarTextArr["overview"] = "Overview"
            navbarTextArr["related"] = "Related"
            navbarTextArr["about"] = "About"

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
                     color = Color.DarkGray
                  )

                  if (it.key == coinDetailScreenState)
                     Divider(thickness = 3.dp, color = mPrimary)
               }
            }
         } // navbar coin detail screen


         // start coin detail screen navigation
         println(coinDetailScreenState)
         Box(
            modifier = Modifier
               .constrainAs(screenCoinDetailState) {
                  top.linkTo(navbarCoinDetailScreen.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
               .height(320.dp)
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
                  .padding(top = 16.dp)
                  .constrainAs(flowRowTag) {
                     top.linkTo(textTag.bottom)
                     start.linkTo(parent.start)
                  }

               when (coinDetailScreenState) {
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
                     modifierHashMap["containerTeam"] = containerTeamModifier
                     modifierHashMap["textTagModifier"] = textTagModifier
                     modifierHashMap["flowRowTagsModifier"] = flowRowTagsModifier

                     Text(
                        text = "Key people and related",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = textTitleModifier
                     )

                     Column(modifier = containerTeamModifier) {
                        dataState.team?.forEach { it ->
                           val nameArr = it.name.split(" ")
                           val initalName = nameArr
                              .take(3)
                              .map { it[0] }
                              .joinToString("")

                           PeopleItem(team = it, initialName = initalName)
                        }
                     }

                     Text(
                        text = "Tags",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = textTagModifier
                     )

                     FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 10.dp,
                        modifier = flowRowTagsModifier
                     ) {

                        dataState.tags?.forEach { tag ->
                           Box(
                              modifier = Modifier
                                 .clickable { println(tag.id) }
                                 .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(100.dp)
                                 )
                                 .padding(10.dp)
                           ) {

                              Text(
                                 text = tag.name,
                                 color = MaterialTheme.colors.primary,
                                 textAlign = TextAlign.Center,
                                 style = MaterialTheme.typography.body2
                              )
                           }
                        }
                     }
                  }
                  "about" -> {
                     Text(
                        text = "about",
                        modifier = Modifier.constrainAs(textTitle) {
                           top.linkTo(parent.top); start.linkTo(parent.start)
                        })
                  }
               }
            }
         }
      }
   }
}
