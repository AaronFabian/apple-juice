package com.aaronfabian.applejuice.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.aaronfabian.applejuice.domain.model.User
import com.aaronfabian.applejuice.presentation.MainViewModel

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
