package com.aaronfabian.applejuice.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.store.NavigationComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
   navController: NavController,
   viewModel: SignInViewModel = hiltViewModel()
) {

   var email by rememberSaveable { mutableStateOf("") }
   var password by rememberSaveable { mutableStateOf("") }
   val scope = rememberCoroutineScope()
   val cmp = NavigationComposition.current
   val context = LocalContext.current
   val state = viewModel.state.collectAsState(initial = null)

   Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
         .fillMaxSize()
         .padding(start = 30.dp, end = 30.dp),
   ) {

      Text(
         text = "Sign in Account",
         color = Color.LightGray,
         fontSize = 42.sp,
         fontWeight = FontWeight.Medium
      )

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
      )

      Text(
         text = "Welcome back..! Get ready to invest..",
         fontWeight = FontWeight.Medium,
         color = Color.Gray,
         fontSize = 15.sp,
      )

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
      )

      TextField(
         value = email,
         onValueChange = { email = it },
         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         shape = RoundedCornerShape(8.dp),
         singleLine = true,
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
         placeholder = {
            Text(text = "Email")
         },
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
         value = password,
         onValueChange = { password = it },
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
         singleLine = true,
         shape = RoundedCornerShape(8.dp),
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         visualTransformation = PasswordVisualTransformation(),
         placeholder = {
            Text(text = "Password")
         },
      )

      Spacer(modifier = Modifier.height(40.dp))

      Button(
         onClick = {
            scope.launch {
               viewModel.loginUser(email, password)
            }

         }, modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = Color.LightGray),
               shape = RoundedCornerShape(16.dp)
            ),
         colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
         ),
         shape = RoundedCornerShape(size = 16.dp)
      ) {
         Text(text = "Sign In", color = Color.White, modifier = Modifier.padding(7.dp))
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
         if (state.value?.isLoading == true) {
            CircularProgressIndicator()
         }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
         text = "Don't have an account ? click here",
         fontWeight = FontWeight.Bold,
         color = Color.LightGray,
         modifier = Modifier.clickable {
            navController.navigate(Screen.SignUpScreen.route)
         }
      )

      Text(text = "or connect with", fontWeight = FontWeight.Medium, color = Color.Gray)

      Row(
         modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
         horizontalArrangement = Arrangement.Center
      ) {
         IconButton(onClick = {}) {
            Icon(
               painter = painterResource(id = R.drawable.ic_google),
               contentDescription = "Google Icon",
               modifier = Modifier.size(50.dp),
               tint = Color.Unspecified
            )
         }

         Spacer(modifier = Modifier.width(20.dp))
         IconButton(onClick = {}) {
            Icon(
               painter = painterResource(id = R.drawable.ic_facebook),
               contentDescription = "Facebook Icon",
               modifier = Modifier.size(50.dp),
               tint = Color.Unspecified
            )
         }

         LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
               if (state.value?.isSuccess?.isNotEmpty() == true) {
                  val success = state.value?.isSuccess
                  Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                  cmp.setIsLoggedIn(true)
                  delay(1500) // go to homescreen
                  navController.navigate(Screen.HomeScreen.route)
               }
            }
         }


         LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
               if (state.value?.isError?.isNotEmpty() == true) {
                  val error = state.value?.isError
                  Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
               }
            }
         }
      }
   }
}


//@Composable
//fun SignInScreen(
//   // state: SignInState,
//   // onSignInClick: () -> Unit
//) {
////   val context = LocalContext.current
////   LaunchedEffect(key1 = state.signInError) {
////      state.signInError?.let { error ->
////         Toast.makeText(context, error, Toast.LENGTH_LONG).show()
////      }
////   }
////
////   Box(
////      modifier = Modifier
////         .fillMaxSize()
////         .padding(16.dp),
////      contentAlignment = Alignment.Center
////   ) {
////      Button(onClick = onSignInClick) {
////         Text(text = "Sign In")
////      }
////   }
//}