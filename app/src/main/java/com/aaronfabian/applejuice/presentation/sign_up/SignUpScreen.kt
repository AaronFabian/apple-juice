package com.aaronfabian.applejuice.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
   navController: NavController,
   viewModel: SignUpViewModel = hiltViewModel()
) {
   var email by rememberSaveable { mutableStateOf("") }
   var name by rememberSaveable { mutableStateOf("") }
   var password by rememberSaveable { mutableStateOf("") }
   var reEnterPassword by rememberSaveable { mutableStateOf("") }
   val keyboardController = LocalSoftwareKeyboardController.current
   val scope = rememberCoroutineScope()
   val context = LocalContext.current
   val state = viewModel.state.collectAsState(initial = null)

   Column(
      modifier = Modifier
         .fillMaxSize()
         .padding(start = 30.dp, end = 30.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   ) {

      Text(
         text = "Create Account",
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
         text = "Enter your email to register",
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
         shape = RoundedCornerShape(8.dp),
         singleLine = true,
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         placeholder = {
            Text(text = "Email")
         },
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
         value = name,
         onValueChange = { name = it },
         shape = RoundedCornerShape(8.dp),
         singleLine = true,
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         placeholder = {
            Text(text = "Username")
         },
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
         value = password,
         onValueChange = { password = it },
         shape = RoundedCornerShape(16.dp),
         singleLine = true,
         visualTransformation = PasswordVisualTransformation(),
         placeholder = {
            Text(text = "Password")
         },
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
         value = reEnterPassword,
         onValueChange = { reEnterPassword = it },
         keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
         colors = TextFieldDefaults.textFieldColors(
            cursorColor = mPrimary,
            textColor = mTextPrimary,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
         ),
         shape = RoundedCornerShape(8.dp),
         singleLine = true,
         visualTransformation = PasswordVisualTransformation(),
         placeholder = {
            Text(text = "Re enter your password")
         },
         modifier = Modifier
            .fillMaxWidth()
            .border(
               BorderStroke(width = 1.dp, color = mPrimary),
               shape = RoundedCornerShape(16.dp)
            ),
      )

      Spacer(modifier = Modifier.height(40.dp))

      Button(
         modifier = Modifier
            .border(
               BorderStroke(width = 1.dp, color = Color.LightGray),
               shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth(),
         colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
         ),
         shape = RoundedCornerShape(size = 16.dp),
         onClick = {
            scope.launch {
               if (password != reEnterPassword) {
                  Toast.makeText(context, "Your password didn't match !", Toast.LENGTH_LONG)
                     .show()

                  return@launch
               }

               viewModel.registerUser(email.trim(), password, username = name)
            }
         }
      ) {
         Text(text = "Sign up", color = Color.White, modifier = Modifier.padding(7.dp))
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
         if (state.value?.isLoading == true) {
            CircularProgressIndicator()
         }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
         text = "Already have an account ? Sign in",
         fontWeight = FontWeight.Bold,
         color = Color.LightGray,
         modifier = Modifier.clickable {
            navController.navigate(Screen.SignInScreen.route)
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
                  Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                  navController.navigate(Screen.SignInScreen.route)
               }
            }
         }


         LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
               if (state.value?.isError?.isNotEmpty() == true) {
                  val error = state.value?.isError
                  Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
               }
            }
         }
      }
   }
}