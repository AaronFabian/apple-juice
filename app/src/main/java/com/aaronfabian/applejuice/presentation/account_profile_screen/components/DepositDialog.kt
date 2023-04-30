package com.aaronfabian.applejuice.presentation.account_profile_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DepositDialog(
   onDismiss: () -> Unit,
   onConfirm: (amount: String, par2: String?, par3: String?) -> Unit
) {

   val keyboard = LocalSoftwareKeyboardController.current

   val tfAmount = remember {
      mutableStateOf("")
   }

   val focusRequester = FocusRequester()

   val onHandleValueChange = { it: String ->
      if (it.isDigitsOnly()) tfAmount.value = it
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
         val tfAmountRef = createRef()
         val spacerTextFieldAndRowButton = createRef()
         val rowBtnRef = createRef()
         val spacerEndRef = createRef()

         Icon(
            painter = painterResource(id = R.drawable.ic_upload),
            contentDescription = "Icon login",
            tint = Color.White,
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
            text = "Please enter amount of deposit",
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
               .height(10.dp)
               .constrainAs(spacerTextAndButtonRef) {
                  top.linkTo(textTitleRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               })


         TextField(
            onValueChange = onHandleValueChange,
            keyboardActions = KeyboardActions(
               onDone = {
                  keyboard?.hide()
               }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = tfAmount.value,
            shape = RoundedCornerShape(8.dp),
            label = { Text(text = "Enter amount", fontSize = 14.sp, color = mTextPrimary) },
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            leadingIcon = {
               Icon(
                  painter = painterResource(id = com.aaronfabian.applejuice.R.drawable.ic_dollar),
                  contentDescription = "Leading Icon for amount label",
                  tint = Color.Green,
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
               .focusRequester(focusRequester)
               .constrainAs(tfAmountRef) {
                  top.linkTo(spacerTextAndButtonRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         )


         Spacer(
            modifier = Modifier
               .fillMaxWidth()
               .height(10.dp)
               .constrainAs(spacerTextFieldAndRowButton) {
                  top.linkTo(tfAmountRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               })


         Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
               .fillMaxWidth()
               .constrainAs(rowBtnRef) {
                  top.linkTo(spacerTextFieldAndRowButton.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               },
         ) {
            Button(
               onClick = { onDismiss() }, colors = ButtonDefaults.buttonColors(
                  backgroundColor = mPrimary,
                  contentColor = Color.White,
               ),
               modifier = Modifier
                  .fillMaxWidth()
                  .weight(1f),
               shape = CircleShape
            ) {
               Text(
                  text = "Cancel",
                  style = MaterialTheme.typography.h6,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center
               )
            }

            Button(
               onClick = {
                  if (tfAmount.value.trim().isEmpty()) return@Button

                  onConfirm(tfAmount.value, null, null)
                  onDismiss()
               }, colors = ButtonDefaults.buttonColors(
                  backgroundColor = mPrimary,
                  contentColor = Color.White,
               ),
               modifier = Modifier
                  .fillMaxWidth()
                  .weight(1f),
               shape = CircleShape
            ) {
               Text(
                  text = "Confirm",
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