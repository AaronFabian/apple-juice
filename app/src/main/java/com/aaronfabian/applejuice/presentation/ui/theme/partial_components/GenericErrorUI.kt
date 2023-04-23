package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

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
fun GenericErrorUI(errorMessage: String) {
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