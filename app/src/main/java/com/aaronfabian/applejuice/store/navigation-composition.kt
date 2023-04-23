package com.aaronfabian.applejuice.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.aaronfabian.applejuice.presentation.MainViewModel

data class NavigationClass(
   val screen: String = "",
   val setScreen: (r: String) -> Unit = {},
)

val NavigationComposition = compositionLocalOf { NavigationClass() }

@Composable
fun NavigationCompositionProvider(viewModel: MainViewModel, children: @Composable () -> Unit) {

   CompositionLocalProvider(
      NavigationComposition provides NavigationClass(
         screen = viewModel.currentRoute.value,
         setScreen = { route: String -> viewModel.setRoute(route) }
      )
   ) {
      children()
   }
}
