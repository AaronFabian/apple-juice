package com.aaronfabian.applejuice.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aaronfabian.applejuice.presentation.coin_detail_screen.CoinDetailScreen
import com.aaronfabian.applejuice.presentation.home_screen.HomeScreen
import com.aaronfabian.applejuice.presentation.people_detail_screen.PeopleDetailScreen
import com.aaronfabian.applejuice.presentation.search_screen.SearchScreen
import com.aaronfabian.applejuice.presentation.sign_in.GoogleAuthClient
import com.aaronfabian.applejuice.presentation.sign_in.SignInScreen
import com.aaronfabian.applejuice.presentation.sign_up.SignUpScreen
import com.aaronfabian.applejuice.presentation.tag_detail_screen.TagDetailScreen
import com.aaronfabian.applejuice.presentation.ui.theme.AppleJuiceTheme
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.BottomNavbar
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


   private val googleAuthClient by lazy {
      GoogleAuthClient(
         context = applicationContext,
         oneTapClient = Identity.getSignInClient(applicationContext)
      )
   }

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         AppleJuiceTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState(
               rememberDrawerState(DrawerValue.Closed)
            )

            var hideBottomBar by remember { mutableStateOf(false) }


            Scaffold(
               bottomBar = { if (hideBottomBar) null else BottomNavbar(navController) }
            ) { it ->
               it.calculateTopPadding()

               NavHost(
                  navController = navController,
                  startDestination = Screen.SignInScreen.route
               ) {

                  composable(route = Screen.SignUpScreen.route) {
                     SignUpScreen(navController = navController)
                     SideEffect { hideBottomBar = true }
                  }

                  composable(route = Screen.SignInScreen.route) {
                     SignInScreen(navController = navController)
                     SideEffect { hideBottomBar = true }
                  }

                  composable(route = Screen.HomeScreen.route) {
                     HomeScreen(navController = navController)
                     SideEffect { hideBottomBar = false }
                  }

                  composable(route = Screen.CoinDetailScreen.route + "/{coinId}") {
                     CoinDetailScreen(navController = navController)
                  }

                  composable(route = Screen.PeopleDetailScreen.route + "/{peopleId}") {
                     PeopleDetailScreen(navController = navController)
                  }

                  composable(route = Screen.TagDetailScreen.route + "/{tagId}") {
                     TagDetailScreen(navController = navController)
                  }

                  composable(route = Screen.SearchCoinScreen.route + "/{query}") {
                     SearchScreen(navController = navController)
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


///////////////
//                     val viewModel = viewModel<SignInViewModel>()
//                     val state by viewModel.state.collectAsStateWithLifecycle()
//
//                     val launcher = rememberLauncherForActivityResult(
//                        contract = ActivityResultContracts.StartIntentSenderForResult(),
//                        onResult = { result ->
//                           if (result.resultCode == RESULT_OK) {
//                              lifecycleScope.launch {
//                                 val signInResult = googleAuthClient.SignInWithIntent(
//                                    intent = result.data ?: return@launch
//                                 )
//
//                                 viewModel.onSignInResult(signInResult)
//                              }
//
//                           }
//                        })
//
//                     LaunchedEffect(key1 = state.isSignInSuccess) {
//                        if (state.isSignInSuccess) {
//                           Toast.makeText(
//                              applicationContext,
//                              "Sign in successfully",
//                              Toast.LENGTH_LONG
//                           ).show()
//                        }
//                     }
//
//                     SignInScreen(
//                        state = state,
//                        onSignInClick = {
//                           lifecycleScope.launch {
//                              val signInIntentSender = googleAuthClient.signIn()
//                              launcher.launch(
//                                 IntentSenderRequest.Builder(
//                                    signInIntentSender ?: return@launch
//                                 ).build()
//                              )
//                           }
//                        }
//                     )