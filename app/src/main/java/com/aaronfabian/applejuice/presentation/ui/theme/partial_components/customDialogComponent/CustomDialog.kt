package com.aaronfabian.applejuice.presentation.ui.theme.partial_components.customDialogComponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
   onDismiss: () -> Unit,
   onConfirm: (param: String?, param2: String?) -> Unit,
   text1: String = "",
   text2: String = "",
   text3: String = "",
   iconId: String = "",
   children: @Composable (onDis: () -> Unit, onCon: (par: String?, par2: String?) -> Unit, text1: String, text2: String, text3: String, iconId: String) -> Unit
) {


   Dialog(
      onDismissRequest = { onDismiss() },
      properties = DialogProperties(
         usePlatformDefaultWidth = false
      )
   ) {
      children(onDismiss, onConfirm, text1, text2, text3, iconId)
   }
}