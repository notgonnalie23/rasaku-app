package app.capstone.rasaku.ui.screen.searchinput

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.History
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.BackPressHandler
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchInputScreen(
    navigateBack: () -> Unit,
    navigateToSearchResult: (String) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val viewModel: SearchInputViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (!WindowInsets.isImeVisible) {
        BackPressHandler(onBackPressed = navigateBack)
    }

    when (uiState) {
        is UiState.Loading -> {
            viewModel.getHistories()
        }

        is UiState.Success -> {
            val histories = (uiState as UiState.Success<SearchInputState>).data.histories
            SearchInputContent(
                histories = histories,
                handleDelete = {
                    viewModel.deleteHistory(it)
                },
                navigateBack = navigateBack,
                navigateToSearchResult = navigateToSearchResult,
                navigateToDetail = navigateToDetail,
                modifier = modifier
            )
        }

        is UiState.Error -> {
            val message = (uiState as UiState.Error).errorMessage
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchInputContent(
    histories: List<History>,
    handleDelete: (Int) -> Unit,
    navigateBack: () -> Unit,
    navigateToSearchResult: (String) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf("") }
    var selectedName by remember { mutableStateOf("")}
    var selectedItem by remember { mutableIntStateOf(-1)}
    var isShowDialog by remember { mutableStateOf(false)}
    val focusRequester = remember { FocusRequester() }

    SearchBar(modifier = modifier.focusRequester(focusRequester),
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            if (query.trim().isNotEmpty()) navigateToSearchResult(query.trim())
        },
        active = true,
        onActiveChange = {},
        placeholder = { Text(stringResource(R.string.search_food)) },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable {
                    navigateBack()
                })
        },
        trailingIcon = {
            Icon(imageVector = Icons.Rounded.Close,
                contentDescription = null,
                modifier = Modifier.clickable {
                    query = ""
                })
        }) {
        histories.forEach { item ->
            if (item.name.uppercase().contains(query.uppercase())) {
                ListItem(leadingContent = {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.img_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(56.dp)
                    )
                }, headlineContent = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }, trailingContent = {
                    Icon(imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedItem = item.id
                            selectedName = item.name
                            isShowDialog = true
                        })
                }, modifier = Modifier.clickable {
                    navigateToDetail(item.id)
                })
            }
        }
    }

    if (isShowDialog) ModalDialog(
        title = stringResource(R.string.delete_food),
        body = stringResource(R.string.alert_delete_collection, selectedName),
        positive = stringResource(R.string.delete),
        negative = stringResource(R.string.cancel),
        onPositiveClick = {
            isShowDialog = false
            handleDelete(selectedItem)
        },
        onNegativeClick = {
            isShowDialog = false
        },
        setShowDialog = {
            isShowDialog = it
        },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchInputScreenPreview() {
    RasakuTheme {
        SearchInputScreen({}, {}, {})
    }
}