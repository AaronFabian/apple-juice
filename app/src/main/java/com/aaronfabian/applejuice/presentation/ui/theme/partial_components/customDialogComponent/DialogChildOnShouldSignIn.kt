package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary

@Composable
fun DialogChildOnShouldSignIn(
   onDismiss: () -> Unit,
   onConfirm: () -> Unit
) {
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
            painter = painterResource(id = R.drawable.baseline_login_24),
            contentDescription = "Icon login",
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
            text = "Oops..looks like you are not logged in! Please login to continue this feature.",
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
                  text = "Close",
                  style = MaterialTheme.typography.h6,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center
               )
            }

            Button(
               onClick = {
                  onConfirm()
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
                  text = "Sign In",
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