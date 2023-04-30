package com.aaronfabian.applejuice.utils

import kotlin.random.Random

fun pickRandomColor(): String {
   val colorPallet = arrayOf("#1b222c", "#323949", "#3d3e51", "#40445a", "#4c5265")
   val pickIndex = Random.nextInt(colorPallet.size)

   return colorPallet[pickIndex]
}