package com.aaronfabian.applejuice.utils

sealed class ResourceDoubleHtpp<T>(
   val data: T? = null,
   val data2: T? = null,
   val message: String? = null
) {
   class Success<T>(data: T, data2: T?) : ResourceDoubleHtpp<T>(data, data2)
   class Error<T>(message: String?, data: T? = null, data2: T?) :
      ResourceDoubleHtpp<T>(data, data2 = data2, message = message)

   class Loading<T>(data: T? = null, data2: T? = null) : ResourceDoubleHtpp<T>(data, data2)
}