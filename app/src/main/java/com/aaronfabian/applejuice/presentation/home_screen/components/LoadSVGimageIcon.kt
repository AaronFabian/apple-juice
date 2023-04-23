package com.aaronfabian.applejuice.presentation.home_screen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun LoadSVGImageIcon(url: String) {
   val imageLoader = ImageLoader.Builder(LocalContext.current).componentRegistry {
      add(SvgDecoder(LocalContext.current))
   }.build()

   CompositionLocalProvider(LocalImageLoader provides imageLoader) {
      val painter = rememberImagePainter(url)

      Image(
         painter = painter,
         contentDescription = "SVG image",
         contentScale = ContentScale.FillBounds,
         modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .size(46.dp)
            .clip(CircleShape),
      )
   }
// TODO: this code caused memory leaks should use coil instead !
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("CheckResult")
@Composable
fun LoadGlideImage(
   url: String,
   contentDescription: String,
) {
   GlideImage(
      model = url,
      contentDescription = contentDescription,
      modifier = Modifier
         .padding(start = 8.dp, end = 8.dp)
         .size(46.dp)
         .clip(CircleShape)
   )
//   val requestOptions = RequestOptions()
//      .diskCacheStrategy(DiskCacheStrategy.ALL)
//      .apply { if (crop) transform(CenterCrop()) }
//
//   val painter: Painter = rememberImagePainter(data = url, builder = { apply { requestOptions } })
//   Image(
//      painter = painter, contentDescription = contentDescription, modifier = Modifier
//         .padding(start = 8.dp, end = 8.dp)
//         .size(46.dp)
//         .clip(CircleShape)
//   )
}