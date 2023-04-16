package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aaronfabian.applejuice.data.remote.dto.Tag
import com.aaronfabian.applejuice.data.remote.dto.Team
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Related(
   navController: NavController,
   modifier: HashMap<String, Modifier>,
   team: List<Team>?,
   tags: List<Tag>?
) {

   Text(
      text = "Key people and related",
      fontSize = 18.sp,
      fontWeight = FontWeight.Medium,
      modifier = modifier["textTitleModifier"]!!
   )

   Column(modifier = modifier["containerTeamModifier"]!!) {
      team?.forEach { it ->

         val nameArr = it.name.split(" ").filter { it.isNotEmpty() }
         val initialName = nameArr
            .take(3)
            .map { it.firstOrNull() }
            .joinToString("")

         PeopleItem(team = it, initialName = initialName, navController)
      }
   }

   Text(
      text = "Tags",
      fontSize = 18.sp,
      fontWeight = FontWeight.Medium,
      modifier = modifier["textTagModifier"]!!, color = mTextPrimary
   )

   FlowRow(
      mainAxisSpacing = 10.dp,
      crossAxisSpacing = 10.dp,
      modifier = modifier["flowRowTagsModifier"]!!
   ) {

      tags?.forEach { tag ->
         Box(
            modifier = Modifier
               .clickable {
                  navController.navigate(Screen.TagDetailScreen.route + "/${tag.id}")
               }
               .border(
                  width = 1.dp,
                  color = mPrimary,
                  shape = RoundedCornerShape(100.dp)
               )
               .padding(10.dp)
         ) {

            Text(
               text = tag.name,
               color = mPrimary,
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.body2
            )
         }
      }
   }

   modifier.clear()
}