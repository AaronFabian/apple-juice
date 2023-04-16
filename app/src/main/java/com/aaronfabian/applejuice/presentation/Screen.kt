package com.aaronfabian.applejuice.presentation

sealed class Screen(val route: String) {

   object HomeScreen : Screen("home_screen")
   object CoinDetailScreen : Screen("coin_detail_screen")
   object AccountProfileScreen : Screen("account_profile_screen")
   object MarketScreen : Screen("market_screen")
   object PortfolioScreen : Screen("portfolio_screen")
   object PeopleDetailScreen : Screen("people_detail_screen")
   object TagDetailScreen : Screen("tag_detail_screen")

}
