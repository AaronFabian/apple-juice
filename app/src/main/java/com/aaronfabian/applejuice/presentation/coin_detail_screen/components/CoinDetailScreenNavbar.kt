package com.aaronfabian.applejuice.presentation.coin_detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.mTextPrimary


@Composable
fun CoinDetailScreenNavbar(currentScreenState: String, onChangeScreen: (key: String) -> Unit) {
   val navbarTextArr = LinkedHashMap<String, String>()
   navbarTextArr["price"] = "Price"
   navbarTextArr["related"] = "Related"
   navbarTextArr["overview"] = "Overview"

   navbarTextArr.forEach { it ->
      Column(
         verticalArrangement = Arrangement.SpaceBetween,
         modifier = Modifier
            .clickable { onChangeScreen(it.key) }
            .width(intrinsicSize = IntrinsicSize.Min)
            .fillMaxHeight()
      ) {
         Text(
            text = it.value,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (it.key == currentScreenState) mPrimary else mTextPrimary
         )

         if (it.key == currentScreenState)
            Divider(thickness = 3.dp, color = mPrimary)
      }
   }
}