package com.aaronfabian.applejuice.presentation.home_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun StockChart(
   changeColor: Float,
   infos: List<String> = emptyList(),
   modifier: Modifier = Modifier,
) {

   val sortedFloatSparkLine = infos.map { it.toFloat() }.sorted()

   // canvas size is 530.0px x 280.0px
   // 1.dp = 3.5px
   // we got 24 sparkline
   // 1 data spacing is 22.08px (x)
   // 1 data spacing is 11.67px (y)
   Canvas(modifier = modifier.fillMaxSize()) {
      val spacingX = size.width / infos.size
      val spacingY = size.height / infos.size

      var x1 = 0f
      var x2 = 0f
      infos.forEachIndexed { index, _ ->
         val sparkValue = sortedFloatSparkLine[index]
         val tolerance = 0.001 // Specify a tolerance level for floating-point comparisons
         val sparkPositionIndex = infos.indexOfFirst {
            (it.toDoubleOrNull()?.minus(sparkValue)?.absoluteValue ?: Double.MAX_VALUE) <= tolerance
         }

         val sparkValueNext = sortedFloatSparkLine.getOrNull(index + 1)
         val sparkPositionIndexNext = infos.indexOfFirst {
            (it.toDoubleOrNull()
               ?.minus(sparkValueNext ?: sparkValue)?.absoluteValue
               ?: Double.MAX_VALUE) <= tolerance
         }

         x2 = (index * spacingX)
         drawLine(
            color = if (changeColor > 0) Color.Green else Color.Red,
            start = Offset(x1, (sparkPositionIndex + 1) * spacingY),
            end = Offset(x2, (sparkPositionIndexNext + 1) * spacingY),
            strokeWidth = 1.dp.toPx()
         )
         x1 = index * spacingX
      }
   }
}

// note

//   val spacing = 100f
//   val transparentGraphColor = remember {
//      graphColor.copy(alpha = .5f)
//   }
//
//   val upperValue = remember {
//      infos.firstOrNull()?.toFloat()?.plus(1)?.roundToInt() ?: 0
//   }
//
//   val lowerValue = remember {
//      infos.lastOrNull()?.toFloat()?.roundToInt() ?: 0
//   }
//
//   val density = LocalDensity.current
//   val textPaint = remember {
//      Paint().apply {
//         color = android.graphics.Color.WHITE
//         textAlign = Paint.Align.CENTER
//         textSize = density.run { 12.sp.toPx() }
//      }
//   }

//   Canvas(modifier = modifier) {
//      val spacePerHour = (size.width - spacing) / infos.size // x axis
//      (0 until infos.size - 1 step 2).forEach { i ->
//         // val info = infos[i]
//         // val hour = info.date.hour
//         // drawContext.canvas.nativeCanvas.apply {
//         // drawText(
//         // hour.toString(),
//         // spacing + i * spacePerHour
//         // size.height - 5,
//         // textPaint
//         // )
//         // }
//      }
//
//      val priceStep = (upperValue - lowerValue) / 5f
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
//
//      var lastX = 0f
//      val strokePath = Path().apply {
//         val height = size.height
//         for (i in infos.indices) {
//            val info = infos[i]
//            val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
//            val leftRatio = (info.toFloat() - lowerValue) / (upperValue - lowerValue)
//            val rightRatio = (nextInfo.toFloat() - lowerValue) / (upperValue - lowerValue)
//
//            val x1 = spacing + i * spacePerHour
//            val y1 = height - spacing - (leftRatio * height).toFloat()
//            val x2 = spacing + (i + 1) * spacePerHour
//            val y2 = height - spacing - (rightRatio * height).toFloat()
//            if (i == 0) {
//               moveTo(
//                  x1,
//                  y1
//               )
//
//               lastX = x1 + x2 / 2f
//               quadraticBezierTo(x1 = x1, y1 = y1, x2 = lastX, y2 = (y1 + y2) / 2f)
//            }
//         }
//      }
//
//      val fillPath = android.graphics.Path(strokePath.asAndroidPath())
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
//   }


// note 2
//      drawRect(
//         color = Color.Black,
//         size = size
//      )
//
//      drawRect(
//         color = Color.Red,
//         topLeft = Offset(100f, 100f),
//         size = Size(100f, 100f),
//         style = Stroke(
//            width = 3.dp.toPx()
//         )
//      )
//
//      drawCircle(
//         brush = Brush.radialGradient(
//            colors = listOf(Color.Red, Color.Yellow),
//            center = center,
//            radius = 100f
//         ),
//         radius = 100f,
//      )
//
//      drawArc(
//         color = Color.Green,
//         startAngle = 0f,
//         sweepAngle = 270f,
//         useCenter = true,
//         topLeft = Offset(size.width - 200f, 10f),
//         size = Size(200f, 200f),
//         style = Stroke(
//            width = 3.dp.toPx()
//         )
//      )
//
//      val path = Path().apply {
//         moveTo(size.width.times(.76f), size.height.times(.72f))
//         cubicTo(
//            size.width.times(.93f),
//            size.height.times(.72f),
//            size.width.times(.98f),
//            size.height.times(.41f),
//            size.width.times(.76f),
//            size.height.times(.40f)
//         )
//         cubicTo(
//            size.width.times(.75f),
//            size.height.times(.21f),
//            size.width.times(.35f),
//            size.height.times(.21f),
//            size.width.times(.38f),
//            size.height.times(.50f)
//         )
//         cubicTo(
//            size.width.times(.25f),
//            size.height.times(.50f),
//            size.width.times(.20f),
//            size.height.times(.69f),
//            size.width.times(.41f),
//            size.height.times(.72f)
//         )
//         close()
//      }
//
//      drawPath(path = path, color = Color.Green)