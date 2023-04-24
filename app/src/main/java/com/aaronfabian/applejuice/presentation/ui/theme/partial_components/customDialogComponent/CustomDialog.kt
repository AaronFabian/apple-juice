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
   onConfirm: () -> Unit,
   children: @Composable (onDis: () -> Unit, onCon: () -> Unit) -> Unit
) {


   Dialog(
      onDismissRequest = { onDismiss() },
      properties = DialogProperties(
         usePlatformDefaultWidth = false
      )
   ) {
      children(onDismiss, onConfirm)
   }
}