package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GenericLoadingUI() {
   Box(modifier = Modifier.fillMaxSize()) {
      CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
   }
}