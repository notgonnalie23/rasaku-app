package app.capstone.rasaku.ui.screen.searchinput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputScreen(
    modifier: Modifier = Modifier
){
    var query by remember { mutableStateOf("") }
    val histories = listOf<String>(
        "First Food",
        "Second Food",
        "Third Food"
    )

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {  },
        active = true,
        onActiveChange = {},
        placeholder = { Text(stringResource(R.string.search_food)) },
        leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable {
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
            if (histories.contains(query)) {
                Row(
                    modifier = modifier.padding(
                        start = 16.dp, end = 24.dp, top = 8.dp, bottom = 8.dp
                    )
                ) {
                    AsyncImage(
                        model = "",
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.placeholder_48x48),
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(56.dp)
                    )
                    Text(
                        text = item, style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            histories.drop(histories.indexOf(item))
                        }
                    )
                }
            }
        }
    }
}