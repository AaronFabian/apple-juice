package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PriceWhenError(errorMessage: String) {
   val responseCodeTicker = errorMessage.split(' ')[1]


   if (responseCodeTicker == "404") {
      Box(modifier = Modifier.fillMaxSize()) {
         Text(
            text = "data is missing",
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp)
         )
      }
   } else {

      Box(modifier = Modifier.fillMaxSize()) {
         Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp)
         )
      }
   }
}