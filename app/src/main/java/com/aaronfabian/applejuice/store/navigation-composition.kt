package com.aaronfabian.applejuice.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.aaronfabian.applejuice.presentation.MainViewModel
import com.aaronfabian.applejuice.utils.User

data class NavigationClass(
   val screen: String = "",
   val setScreen: (s: String) -> Unit = {},
   val user: User = User(),
   val setUser: (u: User) -> Unit = {},
   val isLoggedIn: Boolean = false,
   val setIsLoggedIn: (i: Boolean) -> Unit = {}
)

val NavigationComposition = compositionLocalOf { NavigationClass() }

@Composable
fun NavigationCompositionProvider(viewModel: MainViewModel, children: @Composable () -> Unit) {

   CompositionLocalProvider(
      NavigationComposition provides NavigationClass(
         screen = viewModel.currentRoute.value,
         user = viewModel.user.value,
         isLoggedIn = viewModel.isLoggedIn.value,
         setScreen = { route: String -> viewModel.setRoute(route) },
         setUser = { user: User -> viewModel.setUser(user) },
         setIsLoggedIn = { isLoggedIn -> viewModel.setIsLoggedIn(isLoggedIn) }
      )
   ) {
      children()
   }
}
