package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
      Column(
         modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
         verticalArrangement = Arrangement.spacedBy(25.dp)
      ) {
         Text(
            text = "You are currently not logged in. Please log in to continue purchase feature.",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
         )
      }
   }
}