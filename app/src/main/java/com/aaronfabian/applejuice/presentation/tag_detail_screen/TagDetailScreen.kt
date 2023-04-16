package com.aaronfabian.applejuice.presentation.tag_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.NavbarBackButton

@Composable
fun TagDetailScreen(
   navController: NavController,
   viewModel: TagDetailViewModel = hiltViewModel()
) {
   val state = viewModel.state.value

   if (state.tag != null) {

      NavbarBackButton()

      ConstraintLayout(
         modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .padding(start = 8.dp, end = 8.dp, top = 12.dp)
      ) {
         val tagCircularFrame = createRef()
         val textTagName = createRef()
         val textTagDescription = createRef()

         Box(
            modifier = Modifier
               .offset(y = 12.dp)
               .width(64.dp)
               .fillMaxHeight()
               .constrainAs(tagCircularFrame) {
                  top.linkTo(parent.top)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
               }
         ) {
            Box(
               modifier = Modifier
                  .align(alignment = Alignment.Center)
                  .size(40.dp)
                  .background(color = Color.DarkGray, shape = CircleShape)
            ) {
               Text(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 20.sp,
                  color = Color.LightGray,
                  text = "#",
                  modifier = Modifier.align(alignment = Alignment.Center)
               )
            }
         }

         Text(
            text = state.tag.name,
            color = mTextPrimary,
            fontSize = 18.sp,
            modifier = Modifier.constrainAs(textTagName) {
               top.linkTo(tagCircularFrame.bottom)
               start.linkTo(tagCircularFrame.start)
               end.linkTo(tagCircularFrame.end)
            }
         )

         Text(
            text = state.tag.description,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .width(250.dp)
               .padding(top = 8.dp)
               .height(intrinsicSize = IntrinsicSize.Min)
               .constrainAs(textTagDescription) {
                  top.linkTo(textTagName.bottom)
                  start.linkTo(textTagName.start)
                  end.linkTo(textTagName.end)
               }
         )


      }
   }


   if (state.isLoading) {
      Box(modifier = Modifier.fillMaxSize()) {
         CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
   }

   if (state.error.isNotBlank()) {
      Box(modifier = Modifier.fillMaxSize()) {
         Text(
            text = state.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp)
         )
      }
   }
}

