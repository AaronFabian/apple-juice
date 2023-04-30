package com.aaronfabian.applejuice.presentation.home_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.store.NavigationComposition
import com.aaronfabian.applejuice.utils.setZero

@Composable
fun LoginOrLogoutDialog(
   onDismiss: () -> Unit,
   onConfirm: (par: String?, par2: String?, par3: String?) -> Unit,
   navController: NavController
) {

   val cmp = NavigationComposition.current

   val iconLoginOrLogout = remember {
      if (cmp.isLoggedIn)
         mutableStateOf(R.drawable.ic_logout)
      else
         mutableStateOf(R.drawable.baseline_login_24)
   }

   val textDesc = remember {
      if (cmp.isLoggedIn)
         mutableStateOf("Sign out ? press confirm button to logout screen")
      else
         mutableStateOf("Welcome back !")
   }

   val shouldSignIn = remember {
      if (cmp.isLoggedIn)
         mutableStateOf("Confirm")
      else
         mutableStateOf("Go to sign in")
   }

   val onHandleShouldSignIn = {
      if (cmp.isLoggedIn) {
         setZero(cmp)
         onConfirm("logout", null, null)
      } else
         navController.navigate(Screen.SignInScreen.route)


      onDismiss()
   }

   Card(
      elevation = 5.dp,
      shape = RoundedCornerShape(15.dp),
      modifier = Modifier
         .fillMaxWidth(0.95f)
         .border(1.dp, color = mPrimary, shape = RoundedCornerShape(15.dp))
   ) {
      ConstraintLayout(
         modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
      ) {

         val iconLoginRef = createRef()
         val textTitleRef = createRef()
         val spacerRef = createRef()
         val spacerTextAndButtonRef = createRef()
         val rowBtnRef = createRef()
         val btnConfirmSignInRef = createRef()
         val spacerEndRef = createRef()

         Icon(
            painter = painterResource(id = iconLoginOrLogout.value),
            contentDescription = "Icon Sign in or Sing out",
            tint = Color(0xFF0052FF),
            modifier = Modifier
               .size(60.dp)
               .padding(top = 18.dp)
               .constrainAs(iconLoginRef) {
                  top.linkTo(parent.top)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )

         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(18.dp)
               .constrainAs(spacerRef) {
                  top.linkTo(iconLoginRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )

         Text(
            text = textDesc.value,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .constrainAs(textTitleRef) {
                  top.linkTo(spacerRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )

         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(18.dp)
               .constrainAs(spacerTextAndButtonRef) {
                  top.linkTo(textTitleRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               })


         Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
               .fillMaxWidth()
               .constrainAs(rowBtnRef) {
                  top.linkTo(spacerTextAndButtonRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               },
         ) {
            Button(
               onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                  backgroundColor = mPrimary,
                  contentColor = Color.White,
               ),
               modifier = Modifier
                  .fillMaxWidth()
                  .weight(1f),
               shape = CircleShape
            ) {
               Text(
                  text = "Close",
                  style = MaterialTheme.typography.h6,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center
               )
            }

            Button(
               onClick = onHandleShouldSignIn,
               colors = ButtonDefaults.buttonColors(
                  backgroundColor = mPrimary,
                  contentColor = Color.White,
               ),
               modifier = Modifier
                  .fillMaxWidth()
                  .weight(1f),
               shape = CircleShape
            ) {
               Text(
                  text = shouldSignIn.value,
                  style = MaterialTheme.typography.h6,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center
               )
            }
         }


         Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
            .constrainAs(spacerEndRef) {
               top.linkTo(rowBtnRef.bottom)
               start.linkTo(parent.start)
            }
         )
      }
   }
}