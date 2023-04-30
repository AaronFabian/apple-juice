package com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent

import android.widget.Toast
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.store.NavigationComposition

@Composable
fun DialogChildOnConfirmBuy(
   onConfirm: (par: String?, par2: String?, par3: String?) -> Unit,
   onDismiss: () -> Unit,
   coinName: String,
   coinSymbol: String,
   amount: Float = 1f,
   price: String,
) {

   val context = LocalContext.current
   val cmp = NavigationComposition.current

   val coinAmount = remember {
      mutableStateOf(amount)
   }
   val total = remember {
      mutableStateOf(price.toFloat() * amount)
   }
   val balance = remember {
      mutableStateOf(cmp.user.money)
   }
   val remainingBalance = remember {
      mutableStateOf(cmp.user.money - price.toFloat() * amount)
   }

   val onClickAmount = { option: String ->

      if (coinAmount.value >= 1) {
         if (option == "increase") coinAmount.value++
         else if (option == "decrease") coinAmount.value--

         if (coinAmount.value == 0f) coinAmount.value++

         total.value = price.toFloat() * coinAmount.value
         remainingBalance.value = cmp.user.money - price.toFloat() * coinAmount.value
      }
   }

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
            text = "Confirm payment ?",
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
               Text(text = "$coinName ($coinSymbol)")
               Text(text = "$$price")
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Fee for ($coinSymbol)")
               Text(text = "$0.5")
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Amount")
               Text(text = coinAmount.value.toString())
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.End
            ) {
               IconButton(onClick = {
                  onClickAmount("decrease")
               }) {
                  Icon(
                     painter = painterResource(id = R.drawable.ic_arrow_down),
                     contentDescription = "Icon arrow decrease",
                     tint = Color.Red,
                     modifier = Modifier.scale(2f)
                  )
               }

               Spacer(modifier = Modifier.width(6.dp))

               IconButton(onClick = {
                  onClickAmount("increase")
               }) {
                  Icon(
                     painter = painterResource(id = R.drawable.ic_arrow_up),
                     contentDescription = "Icon arrow up", tint = Color.Green,
                     modifier = Modifier.scale(2f)
                  )
               }
            }

            Divider()

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Balance", fontWeight = FontWeight.Bold)
               Text(text = "$${balance.value}", fontWeight = FontWeight.Bold)
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Total", fontWeight = FontWeight.Bold)
               Text(text = "$${total.value}", fontWeight = FontWeight.Bold)
            }

            Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Text(text = "Remaining balance", fontWeight = FontWeight.Bold)
               Text(
                  text = "$${remainingBalance.value}",
                  fontWeight = FontWeight.Bold,
                  color = if (remainingBalance.value > 0 && coinAmount.value > 0) Color.Green else Color.Red
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
                     if (remainingBalance.value <= 0) {
                        Toast.makeText(
                           context,
                           "Not enough balance! please deposit your money to continue purchase.",
                           Toast.LENGTH_LONG
                        ).show()

                        return@Button
                     }

                     onConfirm(
                        remainingBalance.value.toString(),
                        coinAmount.value.toString(),
                        (price.toDouble() * coinAmount.value).toString()
                     )
                  }, colors = ButtonDefaults.buttonColors(
                     backgroundColor = if (remainingBalance.value > 0f) mPrimary else Color.LightGray,
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