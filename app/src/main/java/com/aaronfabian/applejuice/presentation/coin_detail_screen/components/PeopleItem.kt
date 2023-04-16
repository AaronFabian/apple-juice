package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aaronfabian.applejuice.data.remote.dto.Team
import com.aaronfabian.applejuice.presentation.Screen

@Composable
fun PeopleItem(team: Team, initialName: String, navController: NavController) {
   Row(
      modifier = Modifier
         .clickable {
            println(team.id)
            navController.navigate(Screen.PeopleDetailScreen.route + "/${team.id}")
         }
         .fillMaxWidth()
         .height(64.dp)
   ) {

      // profile
      Box(
         modifier = Modifier
            .width(64.dp)
            .fillMaxHeight()
      ) {
         Box(
            modifier = Modifier
               .align(alignment = Alignment.Center)
               .size(40.dp)
               .background(color = Color.DarkGray, shape = CircleShape)
         ) {
            Text(
               fontWeight = FontWeight.SemiBold,
               fontSize = 16.sp,
               color = Color.LightGray,
               text = initialName,
               modifier = Modifier.align(alignment = Alignment.Center)
            )
         }
      }

      Column(modifier = Modifier.padding(start = 4.dp, top = 8.dp)) {
         Text(
            text = team.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
         )
         Text(
            text = team.position,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
         )
      }
   }


}