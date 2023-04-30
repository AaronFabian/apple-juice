package com.aaronfabian.applejuice.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.presentation.account_profile_screen.AccountProfileViewModel
import com.aaronfabian.applejuice.presentation.account_profile_screen.components.DepositDialog
import com.aaronfabian.applejuice.presentation.account_profile_screen.components.MyCoin
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.GenericLoadingUI
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent.CustomDialog
import com.aaronfabian.applejuice.store.NavigationComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AccountProfileScreen(
   navController: NavController,
   viewModel: AccountProfileViewModel = hiltViewModel()
) {

   val state = viewModel.state.value

   val stateChangeNameState = viewModel.changedNameState.value

   val stateDepositMoney = viewModel.depositMoneyState.value

   val cmp = NavigationComposition.current

   val context = LocalContext.current

   val keyboard = LocalSoftwareKeyboardController.current

   val scope = rememberCoroutineScope()

   val tfName = remember {
      mutableStateOf(cmp.user.name)
   }

   val tfEmail = remember {
      mutableStateOf(cmp.user.email)
   }

   val tfMoney = remember {
      mutableStateOf(cmp.user.money)
   }

   val isShowDialog = remember {
      mutableStateOf(false)
   }

   val btnText = remember {
      if (stateChangeNameState.isLoading)
         mutableStateOf("Loading...")
      else
         mutableStateOf("Change")
   }

   val btnTextDeposit = remember {
      if (stateDepositMoney.isLoading)
         mutableStateOf("Loading...")
      else
         mutableStateOf("Deposit +")
   }


   LaunchedEffect(Unit) {
      viewModel.getUserCoin(cmp.user.uid)
   }

   val onClickTextFieldNameLabel = {
      scope.launch {
         try {
            viewModel.handleChangeName(cmp.user, tfName.value)
            cmp.setUser(
               User(
                  uid = cmp.user.uid,
                  name = tfName.value,
                  email = cmp.user.email,
                  imageUri = cmp.user.imageUri,
                  money = cmp.user.money
               )
            )
            Toast.makeText(context, "Name updated !", Toast.LENGTH_LONG).show()
         } catch (e: Exception) {
            Toast.makeText(context, "An unexpected error occurred !", Toast.LENGTH_LONG).show()
         }
      }
      Unit
   }

   val onClickDepositMoney = { isShowDialog.value = true }

   if (cmp.isLoggedIn) {
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
         val btnChangeName = createRef()
         val spacerEmailRef = createRef()
         val tfEmailRef = createRef()
         val spacerMoneyRef = createRef()
         val tfMoneyRef = createRef()
         val btnDepositMoney = createRef()
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
            keyboardActions = KeyboardActions(
               onDone = {
                  keyboard?.hide()
               }
            ),
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
               .fillMaxWidth(.6f)
               .constrainAs(tfNameRef) {
                  top.linkTo(spacerNameRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(btnChangeName.start)
               }
         )

         Button(
            border = BorderStroke(
               width = 1.dp,
               color = mPrimary
            ),
            colors = ButtonDefaults
               .outlinedButtonColors(
                  backgroundColor = Color.Transparent,
                  contentColor = Color.Transparent,
               ),
            onClick = onClickTextFieldNameLabel,
            modifier = Modifier
               .constrainAs(btnChangeName) {
                  start.linkTo(tfNameRef.end)
                  top.linkTo(tfNameRef.top)
                  bottom.linkTo(tfNameRef.bottom)
                  end.linkTo(parent.end)
               }
         ) {
            Text(text = btnText.value, color = mTextPrimary, fontSize = 14.sp)
         }

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
               .fillMaxWidth(.6f)
               .constrainAs(tfEmailRef) {
                  top.linkTo(spacerEmailRef.bottom)
                  start.linkTo(tfNameRef.start)
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
               .fillMaxWidth(.6f)
               .constrainAs(tfMoneyRef) {
                  top.linkTo(spacerMoneyRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(btnDepositMoney.start)
               }
         )


         Button(
            border = BorderStroke(
               width = 1.dp,
               color = mPrimary
            ),
            colors = ButtonDefaults
               .outlinedButtonColors(
                  backgroundColor = Color.Transparent,
                  contentColor = Color.Transparent,
               ),
            onClick = onClickDepositMoney,
            modifier = Modifier
               .constrainAs(btnDepositMoney) {
                  top.linkTo(tfMoneyRef.top)
                  start.linkTo(tfMoneyRef.end)
                  bottom.linkTo(tfMoneyRef.bottom)
                  end.linkTo(parent.end)
               }
         ) {
            Text(text = "Deposit +", color = mTextPrimary, fontSize = 14.sp)
         }



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


      if (isShowDialog.value) {
         CustomDialog(
            onDismiss = { isShowDialog.value = false },
            onConfirm = { amount, _, _ ->
               scope.launch {
                  try {
                     viewModel.handleDepositMoney(cmp.user, amount!!)

                     val updatedMoney = cmp.user.money + amount.toDouble()

                     cmp.setUser(
                        User(
                           uid = cmp.user.uid,
                           name = cmp.user.name,
                           email = cmp.user.email,
                           imageUri = cmp.user.imageUri,
                           money = updatedMoney
                        )
                     )

                     tfMoney.value = updatedMoney

                     Toast.makeText(context, "Money successfully deposited !", Toast.LENGTH_LONG)
                        .show()
                  } catch (e: Exception) {
                     Toast.makeText(
                        context,
                        "An unexpected error occurred !",
                        Toast.LENGTH_LONG
                     ).show()
                  }
               }
            },
            children = { onDis, onCon, _, _, _, _ ->
               DepositDialog(
                  onDismiss = onDis,
                  onConfirm = onCon
               )
            }
         )
      }
   } else {
      ConstraintLayout(
         modifier = Modifier.fillMaxWidth()
      ) {
         val imageAstronaut = createRef()
         val textWarning = createRef()
         val btnGoToSignInScreen = createRef()

         Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.ic_space_cartoon),
            contentDescription = "space cartoon background",
            modifier = Modifier
               .padding(top = 36.dp)
               .constrainAs(imageAstronaut) {
                  top.linkTo(parent.top)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )


         Text(
            text = "Looks like you are not signed in. click the button below for sign in",
            textAlign = TextAlign.Center,
            modifier = Modifier
               .fillMaxWidth()
               .height(IntrinsicSize.Min)
               .padding(start = 24.dp, end = 24.dp, top = 36.dp)
               .constrainAs(textWarning) {
                  top.linkTo(imageAstronaut.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )

         Button(
            border = BorderStroke(
               width = 1.dp,
               color = mPrimary
            ),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults
               .outlinedButtonColors(
                  backgroundColor = Color.Transparent,
                  contentColor = Color.Transparent,
               ),
            onClick = { navController.navigate(Screen.SignInScreen.route) },
            modifier = Modifier
               .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
               .constrainAs(btnGoToSignInScreen) {
                  top.linkTo(textWarning.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         ) {
            Text(text = "Go to sign in screen", color = Color.White)
         }
      }
   }
}
