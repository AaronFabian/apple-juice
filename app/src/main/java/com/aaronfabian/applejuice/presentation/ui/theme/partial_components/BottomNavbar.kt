package com.aaronfabian.applejuice.presentation.ui.theme.partial_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.utils.dataClass.NavbarHelperData

@Composable
fun BottomNavbar(navController: NavController) {

   var screenState by remember {
      mutableStateOf(Screen.HomeScreen.route)
   }

   ConstraintLayout(
      modifier = Modifier
         .offset(y = (-2).dp)
         .height(56.dp)
         .fillMaxWidth()
   ) {
      val (iconHome, _) = createRefs()
      val (iconAccount, _) = createRefs()
      val (iconMarket, _) = createRefs()
      val (iconPortfolio, _) = createRefs()

      val navbarHelperData = NavbarHelperData.getNavbarData()
      val arrayOfRefModifier = ArrayList<Modifier>()

      val iconHomeRefModifier = Modifier
         .fillMaxHeight()
         .constrainAs(iconHome) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(iconAccount.start)
         }

      val iconAccountRefModifier = Modifier
         .fillMaxHeight()
         .constrainAs(iconAccount) {
            top.linkTo(parent.top)
            start.linkTo(iconHome.end)
            end.linkTo(iconMarket.start)
         }

      val iconMarketRefModifier = Modifier
         .fillMaxHeight()
         .constrainAs(iconMarket) {
            top.linkTo(parent.top)
            start.linkTo(iconAccount.end)
            end.linkTo(iconPortfolio.end)
         }

      val iconPortfolioRefModifier = Modifier
         .fillMaxHeight()
         .constrainAs(iconPortfolio) {
            top.linkTo(parent.top)
            start.linkTo(iconMarket.end)
            end.linkTo(parent.end)
         }

      arrayOfRefModifier.add(iconHomeRefModifier)
      arrayOfRefModifier.add(iconAccountRefModifier)
      arrayOfRefModifier.add(iconMarketRefModifier)
      arrayOfRefModifier.add(iconPortfolioRefModifier)

      navbarHelperData.forEachIndexed { index, it ->
         NavbarIconBtn(
            modifier = arrayOfRefModifier[index],
            navController = navController,
            route = it.route,
            miniText = it.miniText,
            contentDesc = it.contentDescription,
            isSelected = it.route == screenState,
            drawable = it.drawable
         ) {
            screenState = it.route
         }
      }
   }
}

@Composable
fun NavbarIconBtn(
   modifier: Modifier,
   navController: NavController,
   route: String,
   miniText: String,
   contentDesc: String,
   isSelected: Boolean,
   drawable: Int,
   fn: () -> Unit
) {
   IconButton(
      onClick = {
         fn()
         navController.navigate(route)
      }, modifier = modifier
   ) {

      val color = if (isSelected) Color(0xFF0052FF) else Color.LightGray

      Icon(
         tint = color,
         painter = painterResource(id = drawable),
         contentDescription = contentDesc,
         modifier = Modifier.size(28.dp)
      )
      Text(
         text = miniText,
         color = color,
         fontSize = 12.sp,
         modifier = Modifier.padding(top = 34.dp)
      )
   }
}
