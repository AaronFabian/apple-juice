package com.aaronfabian.applejuice.presentation.news_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.news_screen.components.NewsItem
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.store.NavigationComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsScreen(
   navController: NavController,
   viewModel: NewsScreenViewModel = hiltViewModel()
) {

   val state = viewModel.state.value

   val cmp = NavigationComposition.current

   ConstraintLayout(
      modifier = Modifier
         .fillMaxWidth()
         .padding(start = 12.dp, end = 12.dp)
   ) {
      val spacerTextNews = createRef()
      val textNewsRef = createRef()
      val textGreetingRef = createRef()
      val lazyColumnSpacerRef = createRef()
      val lazyColumnRef = createRef()

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(24.dp)
         .constrainAs(spacerTextNews) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
         })

      Text(
         text = "News",
         fontStyle = FontStyle.Italic,
         fontSize = 20.sp,
         color = mTextPrimary,
         modifier = Modifier.constrainAs(textNewsRef) {
            top.linkTo(spacerTextNews.bottom)
            start.linkTo(parent.start)
         })

      Text(
         color = mTextPrimary,
         text = "Hello ${cmp.user.name} ! How's your day ?",
         modifier = Modifier.constrainAs(textGreetingRef) {
            top.linkTo(textNewsRef.bottom)
            start.linkTo(parent.start)
         })

      Spacer(
         modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .constrainAs(lazyColumnSpacerRef) {
               top.linkTo(textGreetingRef.bottom)
               start.linkTo(parent.start)
            }
      )

      if (state.news != null) {
         LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight(.8f)
               .constrainAs(lazyColumnRef) {
                  top.linkTo(lazyColumnSpacerRef.bottom)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }) {
            items(state.news.data) { item ->
               NewsItem(item)
            }
         }
      }
   }
}