package com.aaronfabian.applejuice.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.presentation.coin_detail_screen.CoinDetailScreen
import com.aaronfabian.applejuice.presentation.home_screen.HomeScreen
import com.aaronfabian.applejuice.presentation.people_detail_screen.PeopleDetailScreen
import com.aaronfabian.applejuice.presentation.search_screen.SearchScreen
import com.aaronfabian.applejuice.presentation.sign_in.SignInScreen
import com.aaronfabian.applejuice.presentation.sign_up.SignUpScreen
import com.aaronfabian.applejuice.presentation.tag_detail_screen.TagDetailScreen
import com.aaronfabian.applejuice.presentation.ui.theme.AppleJuiceTheme
import com.aaronfabian.applejuice.presentation.ui.theme.partial_components.BottomNavbar
import com.aaronfabian.applejuice.store.NavigationComposition
import com.aaronfabian.applejuice.store.NavigationCompositionProvider
import com.aaronfabian.applejuice.utils.FirebaseClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


   private val viewModel: MainViewModel by viewModels()

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      installSplashScreen().apply {
         setKeepVisibleCondition {
            viewModel.isLoading.value
         }
      }

      setContent {
         NavigationCompositionProvider(viewModel = viewModel) {
            AppleJuiceTheme {
               val navController = rememberNavController()
               var hideBottomBar by remember { mutableStateOf(false) }

               Scaffold(
                  bottomBar = { if (hideBottomBar) null else BottomNavbar(navController) }
               ) { it ->
                  it.calculateTopPadding()

                  NavHost(
                     navController = navController,
                     startDestination = viewModel.currentRoute.value
                  ) {

                     composable(route = Screen.SignUpScreen.route) {
                        LaunchedEffect(Unit) { hideBottomBar = true }
                        SignUpScreen(navController = navController)
                     }

                     composable(route = Screen.SignInScreen.route) {
                        LaunchedEffect(Unit) { hideBottomBar = true }
                        SignInScreen(navController = navController)
                     }

                     composable(route = Screen.HomeScreen.route) {
                        LaunchedEffect(Unit) { hideBottomBar = false }
                        HomeScreen(navController = navController)
                     }

                     composable(route = Screen.CoinDetailScreen.route + "/{coinId}/{coinColor}") {
                        LaunchedEffect(Unit) { hideBottomBar = false }
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
                        AccountProfileScreen(navController = navController)
                     }

                     composable(route = Screen.MarketScreen.route) {
                        Text(text = "Market screen")
                     }

                     composable(route = Screen.PortfolioScreen.route) {
                        // TODO: As a log out button for a moment

                        val cmp = NavigationComposition.current

                        Text(text = "Portfolio screen")
                        cmp.setUser(User())
                        cmp.setIsLoggedIn(false)
                        FirebaseClass().signOutUser()
                     }
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

// val scaffoldState = rememberScaffoldState(
//   rememberDrawerState(DrawerValue.Closed)
// )

//   private val googleAuthClient by lazy {
//      GoogleAuthClient(
//         context = applicationContext,
//         oneTapClient = Identity.getSignInClient(applicationContext)
//      )
//   }