package com.aaronfabian.applejuice.utils.dataClass

object TablePriceHelperData {

   fun getTableData(): ArrayList<TablePriceDataClass> {
      val tablePriceDataClassArr = ArrayList<TablePriceDataClass>()

      tablePriceDataClassArr.add(TablePriceDataClass("Volume in 24h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Vol change in 24h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Market cap"))
      tablePriceDataClassArr.add(TablePriceDataClass("Market cap in 24h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 15m"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 30m"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 1h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 6h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 12h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 24h"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 7d"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 30d"))
      tablePriceDataClassArr.add(TablePriceDataClass("Change in 1y"))
      tablePriceDataClassArr.add(TablePriceDataClass("All time high price"))
      tablePriceDataClassArr.add(TablePriceDataClass("All time date"))
      tablePriceDataClassArr.add(TablePriceDataClass("Percent from ath"))

      return tablePriceDataClassArr
   }
}