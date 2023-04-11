package com.aaronfabian.applejuice.presentation.home_screen.components

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


//   val painter = rememberGlidePainter(
//      data = url,
//      builder = {
//         error(R.drawable.baseline_error_outline_24) // Optional: specify an error drawable to display if loading fails
//      }
//   )

//   Box(
//      modifier = Modifier.fillMaxSize(),
//      contentAlignment = Alignment.Center
//   ) {
//      when (painter.imageLoader) {
//         is ImageLoadState.Success -> {
//            val svgPainter = painter as? LoadPainter.Svg ?: return@Box
//            val svg: Painter = svgPainter.painter
//            SvgImage(
//               painter = svg,
//               contentDescription = null,
//               modifier = Modifier.fillMaxSize()
//            )
//         }
//         is ImageLoadState.Error -> {
//            Text(text = "Failed to load SVG")
//         }
//         else -> {
//            // Show a loading indicator while the image is loading
//            CircularProgressIndicator(modifier = Modifier.size(32.dp))
//         }
//      }
//   }
}