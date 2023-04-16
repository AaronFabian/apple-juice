package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aaronfabian.applejuice.R

@Composable
fun NavbarBackButton() {

   val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
   IconButton(
      modifier = Modifier.padding(start = 16.dp, end = 16.dp),
      onClick = { dispatcher.onBackPressed() }) {
      Icon(
         tint = Color.LightGray,
         painter = painterResource(id = R.drawable.ic_back_btn),
         contentDescription = "Back Icon Button",
         modifier = Modifier.size(26.dp)
      )
   }
}