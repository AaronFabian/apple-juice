package com.aaronfabian.applejuice.presentation.people_detail_screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary

@Composable
fun PeopleDetailScreen(
   navController: NavController,
   viewModel: PeopleDetailViewModel = hiltViewModel()
) {
   val state = viewModel.state.value

   ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

      val (toolbarRef, _) = createRefs()
      val (dividerRef, _) = createRefs()
      val (frameCircleRef, _) = createRefs()
      val (marginTopFromToolBar, _) = createRefs()
      val (textNameRef, _) = createRefs()
      val (textProjectRef, _) = createRefs()
      val (textDescriptionRef, _) = createRefs()
      val (textDescriptionContentRef, _) = createRefs()
      val (textProjectListRef, _) = createRefs()
      val (lazyColumnListOfCoin, _) = createRefs()

      Row(verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween,
         modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .constrainAs(toolbarRef) {
               top.linkTo(parent.top)
               start.linkTo(parent.start)
            }) {

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

      Divider(
         thickness = .8.dp,
         color = Color.LightGray,
         modifier = Modifier
            .fillMaxWidth()
            .constrainAs(dividerRef) {
               top.linkTo(toolbarRef.bottom)
               start.linkTo(parent.start)
            })

      Divider(
         thickness = 20.dp,
         color = Color.Transparent,
         modifier = Modifier
            .fillMaxWidth()
            .constrainAs(marginTopFromToolBar) {
               top.linkTo(dividerRef.bottom)
               start.linkTo(parent.start)
            }
      )

      Box(modifier = Modifier
         .size(60.dp)
         .background(color = Color.DarkGray, shape = CircleShape)
         .constrainAs(frameCircleRef) {
            top.linkTo(marginTopFromToolBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(textNameRef.start)
         }) {

         var name = "-"
         if (state.coin?.name != null) {
            val nameArr = state.coin.name.split(" ").filter { it.isNotEmpty() }
            name = nameArr
               .take(3)
               .map { it.firstOrNull() }
               .joinToString("")

         }

         Text(
            text = name,
            fontSize = 20.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(alignment = Alignment.Center)
         )
      }

      Text(
         text = state.coin?.name ?: "--",
         fontSize = 18.sp,
         fontWeight = FontWeight.Medium,
         modifier = Modifier
            .width(290.dp)
            .offset(x = (-6).dp)
            .constrainAs(textNameRef) {
               top.linkTo(frameCircleRef.top)
               start.linkTo(frameCircleRef.end)
               end.linkTo(parent.end)
            })

      Text(
         text = "Project Count : ${state.coin?.positions?.size ?: 0}",
         fontSize = 12.sp,
         fontWeight = FontWeight.Normal,
         modifier = Modifier
            .width(290.dp)
            .offset(x = (-6).dp)
            .padding(top = 2.dp)
            .constrainAs(textProjectRef) {
               top.linkTo(textNameRef.bottom)
               start.linkTo(textNameRef.start)
            })

      Text(
         fontWeight = FontWeight.SemiBold,
         fontSize = 18.sp,
         text = "Description",
         modifier = Modifier
            .padding(start = 16.dp, top = 40.dp)
            .constrainAs(textDescriptionRef) {
               top.linkTo(textProjectRef.bottom)
               start.linkTo(parent.start)
            })

      Text(
         text = state.coin?.description ?: "--",
         fontSize = 16.sp,
         modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .constrainAs(textDescriptionContentRef) {
               top.linkTo(textDescriptionRef.bottom)
               start.linkTo(textDescriptionRef.start)
            })

      Text(
         fontWeight = FontWeight.SemiBold,
         fontSize = 18.sp,
         text = "Project list",
         modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .constrainAs(textProjectListRef) {
               top.linkTo(textDescriptionContentRef.bottom)
               start.linkTo(textDescriptionContentRef.start)
            })

      LazyColumn(
         modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .constrainAs(lazyColumnListOfCoin) {
               top.linkTo(textProjectListRef.bottom)
               start.linkTo(parent.start)
            }
      ) {
         items(state.coin?.positions ?: emptyList()) { coin ->
            Row(modifier = Modifier
               .padding(bottom = 8.dp)
               .clickable {
                  navController.navigate(Screen.CoinDetailScreen.route + "/${coin.coin_id}")
               }) {

               Text(text = coin.coin_name, fontSize = 16.sp)

               Text(text = "\t\trole as\t\t", fontSize = 16.sp, color = mPrimary)

               Text(text = coin.position, fontSize = 16.sp, color = mPrimary)
            }

         }
      }
   }

}