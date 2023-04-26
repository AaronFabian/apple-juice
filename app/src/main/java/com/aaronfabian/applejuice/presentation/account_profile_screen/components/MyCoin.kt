package com.aaronfabian.applejuice.presentation.account_profile_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.aaronfabian.applejuice.domain.model.Coins
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyCoin(coins: Coins) {
   Card(
      backgroundColor = Color.Transparent,
      elevation = 8.dp,
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier
         .height(60.dp)
         .fillMaxWidth(.94f)
         .border(
            shape = RoundedCornerShape(8.dp),
            width = 1.dp,
            color = Color(android.graphics.Color.parseColor(coins.coinColor))
         )
         .clickable {
            println(coins.coinId)
         }
   ) {
      ConstraintLayout(
         modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 12.dp)
      ) {
         val glideImageCoinImgRef = createRef()
         val textEqualRef = createRef()
         val textCoinAmount = createRef()
         val textCoinName = createRef()
         val textValue = createRef()


         GlideImage(
            model = coins.coinUri,
            contentDescription = "Coin Icon",
            modifier = Modifier
               .size(42.dp)
               .constrainAs(glideImageCoinImgRef) {
                  top.linkTo(parent.top)
                  start.linkTo(parent.start)
                  bottom.linkTo(parent.bottom)
               }
         )

         Text(
            text = "X",
            fontSize = 14.sp,
            color = mTextPrimary,
            modifier = Modifier
               .padding(start = 8.dp)
               .constrainAs(textEqualRef) {
                  top.linkTo(parent.top)
                  start.linkTo(glideImageCoinImgRef.end)
                  bottom.linkTo(parent.bottom)
               }
         )

         Text(
            text = "${coins.amount}",
            fontSize = 24.sp,
            color = mTextPrimary,
            modifier = Modifier
               .padding(start = 14.dp)
               .constrainAs(textCoinAmount) {
                  top.linkTo(parent.top)
                  start.linkTo(textEqualRef.end)
                  bottom.linkTo(parent.bottom)
               }
         )

         Text(
            text = coins.coinName,
            fontSize = 16.sp,
            color = mTextPrimary,
            modifier = Modifier
               .constrainAs(textCoinName) {
                  top.linkTo(glideImageCoinImgRef.top)
                  end.linkTo(parent.end)
                  bottom.linkTo(textCoinName.top)
               }
         )

         Text(
            text = coins.amount.toString(),
            fontSize = 16.sp,
            color = mTextPrimary,
            modifier = Modifier
               .constrainAs(textValue) {
                  top.linkTo(textCoinName.bottom)
                  end.linkTo(parent.end)
                  bottom.linkTo(glideImageCoinImgRef.bottom)
               }
         )
      }
   }

   Spacer(
      modifier = Modifier
         .fillMaxWidth()
         .height(8.dp)
   )
}