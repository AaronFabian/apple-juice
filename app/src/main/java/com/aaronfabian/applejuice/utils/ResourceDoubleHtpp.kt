package com.aaronfabian.applejuice.utils

sealed class ResourceDoubleHtpp<T, Z>(
   val data: T? = null,
   val data2: Z? = null,
   val message: String? = null
) {
   class Success<T, Z>(data: T, data2: Z?) : ResourceDoubleHtpp<T, Z>(data, data2)
   class Error<T, Z>(message: String?, data: T? = null) :
      ResourceDoubleHtpp<T, Z>(data, message = message)
   
   class Loading<T, Z>(data: T? = null, data2: Z? = null) : ResourceDoubleHtpp<T, Z>(data, data2)
}