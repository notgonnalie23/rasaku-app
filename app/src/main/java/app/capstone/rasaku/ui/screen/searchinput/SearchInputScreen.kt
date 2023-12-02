package app.capstone.rasaku.ui.screen.searchinput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputScreen(
    navigateBack : () -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf("") }
    val histories = remember {
        mutableStateListOf(
            "Food #1",
            "Food #2",
            "Food #3"
        )
    }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { },
        active = true,
        onActiveChange = {},
        placeholder = { Text(stringResource(R.string.search_food)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable {
                    navigateBack()
                }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier.clickable {
                    query = ""
                }
            )
        }
    ) {
        histories.forEach { item ->
            if (item.uppercase().contains(query.uppercase())) {
                ListItem(
                    leadingContent = {
                        AsyncImage(
                            model = "https://placehold.co/56x56/png",
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.placeholder_48x48),
                            contentScale = ContentScale.Crop,
                            modifier = modifier.size(56.dp)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    trailingContent = {

                        Icon(imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                histories.remove(item)
                            }
                        )
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchInputScreenPreview() {
    RasakuTheme {
        SearchInputScreen({})
    }
}