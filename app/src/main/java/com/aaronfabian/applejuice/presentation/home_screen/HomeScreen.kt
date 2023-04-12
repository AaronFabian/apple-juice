package com.aaronfabian.applejuice.presentation.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.home_screen.components.CoinItem

@Composable
fun HomeScreen(
   navController: NavController,
   viewModel: HomeScreenViewModel = hiltViewModel()
) {

   val state = viewModel.state.value
   var tfSearchState by remember {
      mutableStateOf("")
   }

   Box(Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp)) {

      ConstraintLayout {
         val header = createRef()
         val toolBar = createRef()
         val dividerToolbar = createRef()
         val filterBtn = createRef()
         val sortBtn = createRef()
         val containerCoinList = createRef()
         val isLoadingState = createRef()

         Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(header) {
               top.linkTo(parent.top)
               start.linkTo(parent.start)
            }) {
            TextField(
               onValueChange = { it -> tfSearchState = it },
               value = tfSearchState,
               label = { Text(text = "Search Food, Vegetable, etc", fontSize = 12.sp) },
               singleLine = true,
               leadingIcon = {
                  Icon(
                     imageVector = Icons.Rounded.Search,
                     contentDescription = "Search"
                  )
               },
               colors = TextFieldDefaults.textFieldColors(
                  backgroundColor = Color.White,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent
               ),
               shape = RoundedCornerShape(8.dp),
               modifier = Modifier
                  .weight(1f)
                  .border(
                     BorderStroke(width = 1.dp, color = Color.LightGray),
                     shape = RoundedCornerShape(16.dp)
                  )
            )
            IconButton(onClick = { }) {
               Icon(
                  tint = Color.Gray,
                  painter = painterResource(id = R.drawable.ic_ribbon),
                  contentDescription = "Wishlist icon button"
               )
            }

            IconButton(onClick = { }) {
               Icon(
                  tint = Color.Gray,
                  painter = painterResource(id = R.drawable.baseline_notifications_24),
                  contentDescription = "Notification icon button"
               )
            }
         } // Header


         Row(modifier = Modifier
            .offset(y = 20.dp)
            .fillMaxWidth()
            .height(40.dp)
            .constrainAs(toolBar) {
               top.linkTo(header.bottom)
            }) {

            Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier
                  .clickable { }
                  .fillMaxHeight()
                  .weight(1f)
            ) {
               Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier
                     .fillMaxHeight()
                     .wrapContentWidth()
               ) {
                  Text(text = "Spot", color = Color(0xFF0052FF))

                  Spacer(
                     modifier = Modifier
                        .background(Color(0xFF0052FF))
                        .width(40.dp)
                        .height(4.dp)
                  )
               }
            }

            Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier
                  .clickable { }
                  .fillMaxHeight()
                  .weight(1f)
            ) {
               Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier
                     .fillMaxHeight()
                     .wrapContentWidth()
               ) {
                  Text(text = "Futures", color = Color.LightGray)

//                  Spacer(
//                     modifier = Modifier
//                        .background(Color.Blue)
//                        .width(40.dp)
//                        .height(4.dp)
//                  )
               }
            }
         } // toolbar button

         Divider(
            color = Color.LightGray,
            thickness = .7.dp,
            modifier = Modifier
               .fillMaxWidth(fraction = 2f)
               .padding(vertical = 22.dp)
               .constrainAs(dividerToolbar) {
                  top.linkTo(toolBar.bottom)
               }
         )

         Column(modifier = Modifier
            .border(width = .5.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .constrainAs(filterBtn) {
               top.linkTo(dividerToolbar.bottom)
            }) {

            IconButton(
               onClick = { },
            ) {
               Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                  Icon(
                     tint = Color(0xFF363636),
                     painter = painterResource(id = R.drawable.ic_filter),
                     contentDescription = "Button Filter",
                     modifier = Modifier.scale(.8f)
                  )

                  Text(text = "Filter", color = Color(0xFF363636))
               }
            }
         } // help button

         Column(modifier = Modifier
            .offset(x = 8.dp)
            .border(width = .5.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .constrainAs(sortBtn) {
               top.linkTo(dividerToolbar.bottom)
               start.linkTo(filterBtn.end)
            }) {

            IconButton(
               onClick = { },
            ) {
               Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                  Icon(
                     tint = Color(0xFF363636),
                     painter = painterResource(id = R.drawable.ic_sort),
                     contentDescription = "Button Filter",
                     modifier = Modifier.scale(.8f)
                  )

                  Text(text = "Filter", color = Color(0xFF363636))
               }
            }
         } // help button

         // start lazy column
         if (state.coin?.data?.coins != null) {
            LazyColumn(modifier = Modifier
               .padding(top = 20.dp)
               .height(550.dp)
               .constrainAs(containerCoinList) {
                  top.linkTo(filterBtn.bottom)
                  start.linkTo(parent.start)
               }) {
               items(state.coin.data.coins) { coin ->
                  CoinItem(coin, navController)
               }
            }
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
   }
}


