package com.aaronfabian.applejuice.presentation.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.home_screen.components.CoinItem
import com.aaronfabian.applejuice.presentation.home_screen.components.LoginOrLogoutDialog
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent.CustomDialog
import com.aaronfabian.applejuice.store.NavigationComposition

@OptIn(ExperimentalComposeUiApi::class) // ??
@Composable
fun HomeScreen(
   navController: NavController,
   viewModel: HomeScreenViewModel = hiltViewModel()
) {
   val state = viewModel.state.value
   val stateNext = viewModel.stateNext.value

   val cmp = NavigationComposition.current

   var tfSearchState by remember {
      mutableStateOf("")
   }


   val iconLoginOrLoggedOut = remember {
      mutableStateOf(R.drawable.baseline_login_24)
   }

   LaunchedEffect(cmp.isLoggedIn) {
      if (cmp.isLoggedIn)
         iconLoginOrLoggedOut.value = R.drawable.ic_logout
   }


   val isShowDialog = remember {
      mutableStateOf(false)
   }

   val listState = rememberLazyListState()
   val keyboardController = LocalSoftwareKeyboardController.current

   val onReUpdateIcon = {
      iconLoginOrLoggedOut.value = R.drawable.baseline_login_24
   }


   Box(Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp)) {

      ConstraintLayout {
         val header = createRef()
         val toolBar = createRef()
         val dividerToolbar = createRef()
         val filterBtn = createRef()
         val sortBtn = createRef()
         val containerCoinList = createRef()

         Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(header) {
               top.linkTo(parent.top)
               start.linkTo(parent.start)
            }) {
            TextField(
               onValueChange = { it -> tfSearchState = it },
               value = tfSearchState,
               shape = RoundedCornerShape(8.dp),
               label = { Text(text = "Search Coin...", fontSize = 12.sp, color = mTextPrimary) },
               singleLine = true,
               leadingIcon = {
                  Icon(
                     tint = Color.LightGray,
                     imageVector = Icons.Rounded.Search,
                     contentDescription = "Search"
                  )
               },
               colors = TextFieldDefaults.textFieldColors(
                  backgroundColor = Color.Transparent,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                  textColor = mTextPrimary,
                  cursorColor = mPrimary
               ),
               modifier = Modifier
                  .weight(1f)
                  .border(
                     BorderStroke(width = 1.dp, color = Color.LightGray),
                     shape = RoundedCornerShape(16.dp)
                  ),
               keyboardActions = KeyboardActions(onDone = {

                  if (tfSearchState.isEmpty()) return@KeyboardActions

                  keyboardController?.hide()
                  navController.navigate(Screen.SearchCoinScreen.route + "/${tfSearchState}")
               })
            )
            IconButton(onClick = {

            }) {
               Icon(
                  tint = Color.Gray,
                  painter = painterResource(id = R.drawable.ic_ribbon),
                  contentDescription = "Wishlist icon button"
               )
            }

            IconButton(onClick = {
               isShowDialog.value = true
            }) {
               Icon(
                  tint = Color.Gray,
                  painter = painterResource(id = iconLoginOrLoggedOut.value),
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
            .border(width = .5.dp, color = mPrimary, shape = RoundedCornerShape(12.dp))
            .constrainAs(filterBtn) {
               top.linkTo(dividerToolbar.bottom)
            }) {

            IconButton(
               onClick = { },
            ) {
               Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                  Icon(
                     tint = mPrimary,
                     painter = painterResource(id = R.drawable.ic_filter),
                     contentDescription = "Button Filter",
                     modifier = Modifier.scale(.8f)
                  )

                  Text(text = "Filter", color = mPrimary)
               }
            }
         } // help button

         Column(modifier = Modifier
            .offset(x = 8.dp)
            .border(width = .5.dp, color = mPrimary, shape = RoundedCornerShape(12.dp))
            .constrainAs(sortBtn) {
               top.linkTo(dividerToolbar.bottom)
               start.linkTo(filterBtn.end)
            }) {

            IconButton(
               onClick = { },
            ) {
               Row(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                  Icon(
                     tint = mPrimary,
                     painter = painterResource(id = R.drawable.ic_sort),
                     contentDescription = "Button Filter",
                     modifier = Modifier.scale(.8f)
                  )

                  Text(text = "Filter", color = mPrimary)
               }
            }
         } // help button

         // start lazy column
         if (state.coin?.data?.coins != null) {
            LazyColumn(state = listState, modifier = Modifier
               .padding(top = 20.dp)
               .height(550.dp)
               .constrainAs(containerCoinList) {
                  top.linkTo(filterBtn.bottom)
                  start.linkTo(parent.start)
               }) {
               items(state.coin.data.coins) { coin ->
                  CoinItem(coin, navController)

                  if (coin.name == viewModel.lastCoinName) {
                     viewModel.getCoinNextList()
                  }
                  // println("${coin.name} -> ${viewModel.lastCoinName}")
               }


               if (stateNext.isLoading) {
                  item {
                     Row(
                        modifier = Modifier
                           .fillMaxWidth()
                           .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                     ) {
                        CircularProgressIndicator()
                     }
                  }
               }

               if (stateNext.coin?.data?.coins != null) {

               }

               if (stateNext.error.isNotBlank()) {

                  item {
                     Row(
                        modifier = Modifier
                           .fillMaxWidth()
                           .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                     ) {
                        Text(text = "Fetching please wait")
                     }
                  }
               }
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


   if (isShowDialog.value) {
      CustomDialog(
         onDismiss = { isShowDialog.value = false },
         onConfirm = { option, _, _ ->
            if (option == "logout") onReUpdateIcon()
         },
         children = { onDis, onCon, _, _, _, _ ->
            LoginOrLogoutDialog(onDis, onCon, navController)
         }
      )
   }
}


