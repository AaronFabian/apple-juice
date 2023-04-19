package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.aaronfabian.applejuice.domain.model.CoinGraph
import kotlin.math.roundToInt

@Composable
fun CoinDetailCanvas(
   coinGraphData: CoinGraph,
   graphColor: Color = Color.Green,
   modifier: Modifier
) {

   val coinGraph = coinGraphData.data.coins?.getOrNull(0)?.sparkline

   val spacing = 20f
   val transparentGraphColor = remember {
      graphColor.copy(alpha = .5f)
   }

   val upperValue = remember {
      coinGraph?.firstOrNull()?.toFloat()?.plus(1)?.roundToInt() ?: 0
   }

   val lowerValue = remember {
      coinGraph?.lastOrNull()?.toFloat()?.roundToInt() ?: 0
   }

   val density = LocalDensity.current
   val textPaint = remember {
      Paint().apply {
         color = android.graphics.Color.WHITE
         textAlign = Paint.Align.CENTER
         textSize = density.run { 12.sp.toPx() }
      }
   }

   Canvas(modifier = modifier) {

      drawRect(
         color = Color.Red,
         topLeft = Offset(x = 50f, y = 50f),
         size = Size(width = 20f, height = 20f)
      )


      val spacePerHour = (size.width - spacing) / (coinGraph?.size ?: 0) // x axis
      (0 until (coinGraph?.size?.minus(1) ?: 0) step 2).forEach { i ->
         // val info = coinGraph?.get(i)
         // val hour = info
         //         drawContext.canvas.nativeCanvas.apply {
         //            drawText(
         //               "t",
         //               spacing + i * spacePerHour,
         //               size.height - 5,
         //               textPaint
         //            )
         //         }
      }

//      val priceStep = (upperValue - lowerValue) / 3f
//      (0..5).forEach { i ->
//         drawContext.canvas.nativeCanvas.apply {
//            drawText(
//               round(lowerValue + priceStep * i).toString(),
//               30f,
//               size.height - spacing - i * size.height / 5f,
//               textPaint
//            )
//         }
//      }

      //      var lastX = 0f
      //      val strokePath = androidx.compose.ui.graphics.Path().apply {
      //         val height = size.height
      //         if (coinGraph != null) {
      //            for (i in coinGraph.indices) {
      //               val info = coinGraph[i]
      //               println(info)
      //               val nextInfo = coinGraph.getOrNull(i + 1) ?: coinGraph.last()
      //               val leftRatio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)
      //               val rightRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)
      //
      //               val x1 = spacing + i * spacePerHour
      //               val y1 = height - spacing - (leftRatio * height).toFloat()
      //               val x2 = spacing + (i + 1) * spacePerHour
      //               val y2 = height - spacing - (rightRatio * height).toFloat()
      //               if (i == 0) {
      //                  moveTo(
      //                     x1,
      //                     y1
      //                  )
      //
      //                  lastX = x1 + x2 / 2f
      //                  quadraticBezierTo(x1 = x1, y1 = y1, x2 = lastX, y2 = (y1 + y2) / 2f)
      //               }
      //            }
      //         }
      //      }

      //      val fillPath = Path(strokePath.asAndroidPath())
      //         .asComposePath()
      //         .apply {
      //            lineTo(lastX, size.height - spacing)
      //            lineTo(spacing, size.height - spacing)
      //            close()
      //         }
      //
      //      drawPath(
      //         path = fillPath,
      //         brush = Brush.verticalGradient(
      //            colors = listOf(
      //               transparentGraphColor,
      //               Color.Transparent
      //            ),
      //            endY = size.height - spacing
      //         )
      //      )
      //      drawPath(
      //         path = strokePath,
      //         color = graphColor,
      //         style = Stroke(
      //            width = 3.dp.toPx(),
      //            cap = StrokeCap.Round
      //         )
      //      )
   }
}