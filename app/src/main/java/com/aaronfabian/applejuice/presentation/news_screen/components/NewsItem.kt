package com.aaronfabian.applejuice.presentation.news_screen.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.aaronfabian.applejuice.data.remote.dto.DataXXX
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary
import com.aaronfabian.applejuice.utils.StringUtil
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
   data: DataXXX
) {
   val context = LocalContext.current

   val date = StringUtil.toReadAbleDateFromTimestamp("yyyy-MM-dd", data.published_on.toLong())


   ConstraintLayout(
      modifier = Modifier
         .clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            context.startActivity(intent)
         }
         .fillMaxWidth()
         .height(140.dp)
   ) {
      val spacerTopRef = createRef()
      val glideImageThumbnail = createRef()
      val textBodyPreview = createRef()
      val textPublisher = createRef()
      val textReleaseDate = createRef()

      Spacer(modifier = Modifier
         .fillMaxWidth()
         .height(8.dp)
         .constrainAs(spacerTopRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
         })

      GlideImage(
         model = data.imageurl,
         contentDescription = "Image Uri",
         modifier = Modifier
            .size(90.dp)
            .constrainAs(glideImageThumbnail) {
               top.linkTo(spacerTopRef.bottom)
               start.linkTo(parent.start)
            })

      Text(
         lineHeight = 20.sp,
         overflow = TextOverflow.Ellipsis,
         fontSize = 16.sp,
         color = mTextPrimary,
         text = data.body,
         modifier = Modifier
            .height(90.dp)
            .fillMaxWidth(.74f)
            .padding(start = 16.dp)
            .constrainAs(textBodyPreview) {
               top.linkTo(glideImageThumbnail.top)
               start.linkTo(glideImageThumbnail.end)
            }
      )

      Text(
         color = mTextPrimary,
         fontSize = 14.sp,
         text = "Source : ${data.source_info.name}",
         modifier = Modifier
            .padding(top = 2.dp)
            .constrainAs(textPublisher) {
               top.linkTo(glideImageThumbnail.bottom)
               start.linkTo(glideImageThumbnail.start)
            })

      Text(
         color = mTextPrimary,
         fontSize = 14.sp,
         text = date,
         modifier = Modifier
            .padding(top = 2.dp)
            .constrainAs(textReleaseDate) {
               top.linkTo(textBodyPreview.bottom)
               end.linkTo(textBodyPreview.end)
            })

   }
}