package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.involvedapps.beefirstonvideo.utils.views.hideKeyboard

@Composable
internal fun VideoIdTextFieldView(
    modifier: Modifier = Modifier,
    text: String,
    onSearchedTextChange: (String) -> Unit,
    onClickSearch: (String) -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = text,
            onValueChange = { newText ->
                onSearchedTextChange(newText)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (text.isNotEmpty()) {
                    hideKeyboard(context, view)
                    onClickSearch(text)
                }
            }),
            label = { Text("Video Id") },
        )
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                if (text.isNotEmpty()) {
                    hideKeyboard(context, view)
                    onClickSearch(text)
                }
            },
            enabled = text.isNotEmpty()
        ) {
            Text("Valider")
        }
    }
}