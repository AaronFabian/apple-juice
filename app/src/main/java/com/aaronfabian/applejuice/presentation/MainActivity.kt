package com.aaronfabian.applejuice.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aaronfabian.applejuice.presentation.coin_detail_screen.CoinDetailScreen
import com.aaronfabian.applejuice.presentation.home_screen.HomeScreen
import com.aaronfabian.applejuice.presentation.ui.theme.AppleJuiceTheme
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.BottomNavbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         AppleJuiceTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState(
               rememberDrawerState(DrawerValue.Closed)
            )

            Scaffold(
               bottomBar = { BottomNavbar(navController) }
            ) { it ->
               it.calculateTopPadding()

               NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

                  composable(route = Screen.HomeScreen.route) {
                     HomeScreen(navController = navController)
                  }

                  composable(route = Screen.CoinDetailScreen.route + "/{coinId}") {
                     CoinDetailScreen(navController = navController)
                  }

                  composable(route = Screen.AccountProfileScreen.route) {
                     Text(text = "Account screen")
                  }

                  composable(route = Screen.MarketScreen.route) {
                     Text(text = "Market screen")
                  }

                  composable(route = Screen.PortfolioScreen.route) {
                     Text(text = "Portfolio screen")
                  }
               }
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
   AppleJuiceTheme {

   }
}