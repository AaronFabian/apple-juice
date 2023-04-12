package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider() {
   Divider(
      color = Color(0xFFF1F1F1),
      modifier = Modifier
         .width(1.dp)
         .height(32.dp)
   )
}