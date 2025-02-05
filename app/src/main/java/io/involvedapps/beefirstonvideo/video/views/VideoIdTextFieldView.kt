package io.involvedapps.beefirstonvideo.video.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
internal fun VideoIdTextFieldView(
    modifier: Modifier = Modifier,
    onClickSearch: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (text.isNotEmpty()) {
                    onClickSearch(text)
                }
            }),
            label = { Text("Video Id") },
        )
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                if (text.isNotEmpty()) {
                    onClickSearch(text)
                }
            },
            enabled = text.isNotEmpty()
        ) {
            Text("Valider")
        }
    }
}