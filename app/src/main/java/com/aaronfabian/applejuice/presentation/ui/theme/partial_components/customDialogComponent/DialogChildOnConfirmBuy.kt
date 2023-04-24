package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary

@Composable
fun DialogChildOnConfirmBuy(
   onConfirm: () -> Unit,
   onDismiss: () -> Unit
) {

   Card(
      elevation = 5.dp,
      shape = RoundedCornerShape(15.dp),
      modifier = Modifier
         .fillMaxWidth(0.95f)
         .border(1.dp, color = mPrimary, shape = RoundedCornerShape(15.dp))
   ) {
      Column(
         modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
         verticalArrangement = Arrangement.spacedBy(25.dp)
      ) {
         Text(
            text = "Your selected items. Please select a payment method to continue",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
         )

         Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
         ) {
            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Bitcoin (BTC)")
               Text(text = "$32000")
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Fee for (BTC)")
               Text(text = "$0.5")
            }

            Divider()

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Total", fontWeight = FontWeight.Bold)
               Text(text = "$32000.5", fontWeight = FontWeight.Bold)
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.SpaceAround
            ) {
               Image(
                  painter = painterResource(id = R.drawable.ic_coin),
                  contentDescription = "Test dialog",
                  modifier = Modifier
                     .fillMaxWidth()
                     .clip(RoundedCornerShape(15.dp))
                     .clickable { }
               )

               Image(
                  painter = painterResource(id = R.drawable.ic_coin),
                  contentDescription = "Test dialog",
                  modifier = Modifier
                     .fillMaxWidth()
                     .clip(RoundedCornerShape(15.dp))
                     .clickable { }
               )
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.spacedBy(30.dp),
               verticalAlignment = Alignment.CenterVertically
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
                     text = "Buy",
                     style = MaterialTheme.typography.h6,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Center
                  )
               }
            }
         }
      }
   }
}