package com.aaronfabian.applejuice

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppleJuiceApplication : Application() {

   init {
      if(BuildConfig.DEBUG)
         StrictMode.enableDefaults();
   }

}