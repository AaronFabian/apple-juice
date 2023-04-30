package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot


@Composable
fun CoinDetailCanvas(
   coinGraphData: List<String?>?,
   graphColor: Color = Color.Green,
   modifier: Modifier
) {

   val linePlotLines = remember {
      mutableStateListOf<LinePlot.Line>()
   }

   val pointValues = remember {
      mutableStateListOf<DataPoint>()
   }

   val coinGraphStr = coinGraphData

   val dataPoints = arrayListOf<DataPoint>()
   val numberList = arrayListOf<Float>()
   var xPos = 0f

   linePlotLines.clear()
   coinGraphStr?.forEach { spark ->
      val value = spark?.toFloatOrNull()
      if (value != null) {
         numberList.add(value)
         dataPoints.add(DataPoint(xPos, value))

         xPos++
      }
   }

   if (dataPoints.isNotEmpty()) {

      linePlotLines.add(
         LinePlot.Line(
            dataPoints,
            LinePlot.Connection(color = Color.Red, strokeWidth = 2.dp),
            LinePlot.Intersection(color = Color.Red, radius = 3.dp),
            LinePlot.Highlight(color = Color.Red)
         )
      )

      if (linePlotLines.isNotEmpty()) {
         LineGraph(
            modifier = modifier,
            plot = LinePlot(
               lines = linePlotLines,
               grid = LinePlot.Grid(graphColor, steps = 4),
               selection = LinePlot.Selection(
                  enabled = true,
                  highlight = LinePlot.Connection(
                     color = Color.LightGray,
                     strokeWidth = 1.dp,
                     pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                  )
               ),
               isZoomAllowed = true
            ),
         )
      }

   }
}