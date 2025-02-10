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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.involvedapps.beefirstonvideo.R
import io.involvedapps.beefirstonvideo.ui.theme.BeeFirstOnVideoTheme
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
            modifier = Modifier.padding(top = 8.dp),
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
            label = { Text(stringResource(R.string.video_id_label)) },
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
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

@Preview
@Composable
private fun VideoIdTextFieldViewPreview() {
    BeeFirstOnVideoTheme {
        VideoIdTextFieldView(
            text = "",
            onSearchedTextChange = {},
            onClickSearch = {},
        )
    }
}