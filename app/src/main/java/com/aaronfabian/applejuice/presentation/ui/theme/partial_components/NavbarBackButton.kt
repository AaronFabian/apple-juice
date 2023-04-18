package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaronfabian.applejuice.R

@Composable
fun NavbarBackButton(text: String = "", showIcon: Boolean = false) {

   Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
         .height(56.dp)
         .fillMaxWidth()
   ) {

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

      Text(
         text = text,
         fontSize = 18.sp,
         fontWeight = FontWeight.Bold,
         color = Color.LightGray
      )

      IconButton(
         modifier = Modifier.padding(start = 16.dp, end = 16.dp),
         onClick = {
            // TODO: implement bookmark navigation
            if (!showIcon) return@IconButton
         }) {
         Icon(
            tint = if (showIcon) Color.LightGray else Color.Transparent,
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = "Back Icon Button",
            modifier = Modifier.size(26.dp)
         )
      }
   }
}