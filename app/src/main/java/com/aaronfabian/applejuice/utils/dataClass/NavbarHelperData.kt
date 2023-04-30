package com.aaronfabian.applejuice.utils.dataClass

import com.aaronfabian.applejuice.R
import com.aaronfabian.applejuice.presentation.Screen

object NavbarHelperData {

   fun getNavbarData(): ArrayList<NavbarDataClass> {
      val navbarData = ArrayList<NavbarDataClass>()

      val nav1 = NavbarDataClass(
         miniText = "Home",
         drawable = R.drawable.ic_home,
         contentDescription = "Home Icon Button",
         route = Screen.HomeScreen.route
      )
      navbarData.add(nav1)

      val nav2 = NavbarDataClass(
         miniText = "Account",
         drawable = R.drawable.ic_account,
         contentDescription = "Account Icon Button",
         route = Screen.AccountProfileScreen.route
      )
      navbarData.add(nav2)

      val nav3 = NavbarDataClass(
         miniText = "Market",
         drawable = R.drawable.ic_graph,
         contentDescription = "Market Icon Button",
         route = Screen.MarketScreen.route
      )
      navbarData.add(nav3)

      val nav4 = NavbarDataClass(
         miniText = "News",
         drawable = R.drawable.baseline_newspaper_24,
         contentDescription = "News",
         route = Screen.NewsScreen.route
      )
      navbarData.add(nav4)

      return navbarData
   }
}
