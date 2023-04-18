package com.aaronfabian.applejuice.presentation.search_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aaronfabian.applejuice.presentation.Screen
import com.aaronfabian.applejuice.presentation.home_screen.components.LoadSVGImageIcon
import com.aaronfabian.applejuice.presentation.ui.theme.mPrimary
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.NavbarBackButton
import com.aaronfabian.applejuice.utils.StringUtil
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
   navController: NavController,
   viewModel: SearchScreenViewModel = hiltViewModel()
) {

   val state = viewModel.state.value

   if (state.coin != null) {
      Column {

         NavbarBackButton(text = "search for ${viewModel.searchQuery}")

         FlowRow(
            mainAxisAlignment = MainAxisAlignment.Center,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp,
            modifier = Modifier
               .padding(start = 16.dp)
               .fillMaxWidth()
         ) {


            state.coin.data.coins.forEach { coin ->

               Card(
                  onClick = {
                     val strCoinId = StringBuilder()

                     val getCoinSymbol = coin.symbol
                     val getCoinName = coin.name

                     strCoinId.append(getCoinSymbol?.toLowerCase() ?: "er")
                     strCoinId.append('-')
                     strCoinId.append(
                        getCoinName
                           ?.toLowerCase()
                           ?.replace(' ', '-')
                     )

                     println(strCoinId)

                     navController.navigate(Screen.CoinDetailScreen.route + "/${strCoinId}")

                  },
                  modifier = Modifier
                     .width(100.dp)
                     .height(IntrinsicSize.Min),
                  shape = RoundedCornerShape(6.dp),
                  elevation = 10.dp,
                  border = BorderStroke(width = 1.dp, color = mPrimary)
               ) {
                  Column(
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center,
                     modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                  ) {
                     LoadSVGImageIcon(url = coin.iconUrl ?: "")

                     Text(
                        text = coin.symbol ?: "--",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                     )

                     var price = coin.price

                     if (price == null) price = "0"
                     else price = StringUtil.toFixDecimal(coin.price.toString())

                     Text(
                        text = price,
                        textAlign = TextAlign.Center
                     )
                  }
               }
            }
         }
      }
   }

   if (state.isLoading) {
      Row(
         modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
         horizontalArrangement = Arrangement.Center
      ) {
         Text(text = "Please wait...")
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