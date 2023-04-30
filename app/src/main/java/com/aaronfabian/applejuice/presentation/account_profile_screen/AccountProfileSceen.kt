package com.aaronfabian.applejuice.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.account_profile_screen.AccountProfileViewModel
import com.aaronfabian.applejuice.presentation.account_profile_screen.components.MyCoin
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericLoadingUI
import com.aaronfabian.applejuice.store.NavigationComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AccountProfileScreen(
   navController: NavController,
   viewModel: AccountProfileViewModel = hiltViewModel()
) {

   val state = viewModel.state.value

   val cmp = NavigationComposition.current

   val tfName = remember {
      mutableStateOf(cmp.user.name)
   }

   val tfEmail = remember {
      mutableStateOf(cmp.user.email)
   }

   val tfMoney = remember {
      mutableStateOf(cmp.user.money)
   }


   LaunchedEffect(Unit) {
      viewModel.getUserCoin(cmp.user.uid)
   }

   ConstraintLayout(
      modifier = Modifier
         .padding(start = 8.dp, end = 8.dp)
         .fillMaxWidth()
   )
   {
      val spacerTopRef = createRef()
      val imageProfileRef = createRef()
      val spacerNameRef = createRef()
      val tfNameRef = createRef()
      val spacerEmailRef = createRef()
      val tfEmailRef = createRef()
      val spacerMoneyRef = createRef()
      val tfMoneyRef = createRef()
      val spacerMyCoinList = createRef()
      val lazyColumnCoinListRef = createRef()
      val loadingUiRef = createRef()

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .constrainAs(spacerTopRef) {
               top.linkTo(parent.top)
               start.linkTo(parent.start)
            }
      )

      GlideImage(
         model = "https://www.gravatar.com/avatar/000000?d=retro",
         contentDescription = "Profile image Uri",
         modifier = Modifier
            .size(120.dp)
            .constrainAs(imageProfileRef) {
               top.linkTo(spacerTopRef.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            })

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(24.dp)
         .constrainAs(spacerNameRef) {
            top.linkTo(imageProfileRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
         })

      TextField(
         onValueChange = { it -> tfName.value = it },
         value = tfName.value,
         shape = RoundedCornerShape(8.dp),
         label = { Text(text = "Name", fontSize = 14.sp, color = mTextPrimary) },
         singleLine = true,
         textStyle = TextStyle(fontSize = 18.sp),
         leadingIcon = {
            Icon(
               painter = painterResource(id = com.aaronfabian.applejuice.R.drawable.ic_account_default),
               contentDescription = "Leading Icon for name label",
               tint = mTextPrimary,
               modifier = Modifier.size(30.dp)
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
            .fillMaxWidth(.80f)
            .constrainAs(tfNameRef) {
               top.linkTo(spacerNameRef.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            }
      )

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(24.dp)
         .constrainAs(spacerEmailRef) {
            top.linkTo(tfNameRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
         })

      TextField(
         onValueChange = { it -> tfEmail.value = it },
         value = tfEmail.value,
         shape = RoundedCornerShape(8.dp),
         label = { Text(text = "Email", fontSize = 14.sp, color = mTextPrimary) },
         singleLine = true,
         textStyle = TextStyle(fontSize = 18.sp),
         leadingIcon = {
            Icon(
               painter = painterResource(id = com.aaronfabian.applejuice.R.drawable.ic_email),
               contentDescription = "Leading Icon for email label",
               tint = mTextPrimary,
               modifier = Modifier.size(30.dp)
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
            .fillMaxWidth(.80f)
            .constrainAs(tfEmailRef) {
               top.linkTo(spacerEmailRef.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            }
      )

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(24.dp)
         .constrainAs(spacerMoneyRef) {
            top.linkTo(tfEmailRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
         })

      TextField(
         onValueChange = { it -> tfMoney.value = it.toDouble() },
         value = tfMoney.value.toString(),
         shape = RoundedCornerShape(8.dp),
         label = { Text(text = "Balance in USD", fontSize = 14.sp, color = mTextPrimary) },
         singleLine = true,
         readOnly = true,
         textStyle = TextStyle(fontSize = 18.sp, color = Color.Green),
         leadingIcon = {
            Icon(
               painter = painterResource(id = com.aaronfabian.applejuice.R.drawable.ic_dollar),
               contentDescription = "Leading Icon for money label",
               tint = mTextPrimary,
               modifier = Modifier.size(30.dp)
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
            .fillMaxWidth(.80f)
            .constrainAs(tfMoneyRef) {
               top.linkTo(spacerMoneyRef.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            }
      )

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(24.dp)
         .constrainAs(spacerMyCoinList) {
            top.linkTo(tfMoneyRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
         })

      if (state.isSuccess?.isNotEmpty() == true) {
         val listOfMyCoin = state.isSuccess.map { it.value }

         LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
               .fillMaxHeight(.43f)
               .fillMaxWidth()
               .constrainAs(lazyColumnCoinListRef) {
                  top.linkTo(spacerMyCoinList.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         ) {
            items(items = listOfMyCoin) { item ->
               MyCoin(item, navController)
            }
         }
      }


      if (state.isLoading) {
         Box(
            modifier = Modifier
               .fillMaxSize()
               .constrainAs(loadingUiRef) {
                  top.linkTo(spacerMyCoinList.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
                  bottom.linkTo(parent.bottom)
               }
         ) {
            GenericLoadingUI()
         }
      }
   }

}
