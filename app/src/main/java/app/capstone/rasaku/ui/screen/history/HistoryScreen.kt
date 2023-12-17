package app.capstone.rasaku.ui.screen.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.R
import app.capstone.rasaku.model.History
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.common.UiState
import app.capstone.rasaku.ui.component.EmptyView
import app.capstone.rasaku.ui.component.ListComponent
import app.capstone.rasaku.ui.component.Loading
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme
import app.capstone.rasaku.utils.getAsTime

@Composable
fun HistoryScreen(
    navigateToDetail: (Int) -> Unit, modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel: HistoryViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {
            Loading()
            viewModel.getHistories()
        }

        is UiState.Success -> {
            val histories = (uiState as UiState.Success<HistoryState>).data.histories
            HistoryContent(
                histories = histories,
                navigateToDetail = navigateToDetail,
                handleDelete = {
                    viewModel.deleteHistory(it)
                },
                modifier = modifier,
            )
        }

        is UiState.Error -> EmptyView(message = (uiState as UiState.Error).errorMessage)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryContent(
    histories: List<History>,
    navigateToDetail: (Int) -> Unit,
    handleDelete: (Int) -> Unit,
    modifier: Modifier,
) {
    var selectedItem by remember { mutableIntStateOf(-1) }
    var selectedName by remember { mutableStateOf("") }
    var isShowDialog by remember { mutableStateOf(false) }

    if (histories.isNotEmpty()) {
        LazyColumn {
            items(histories) { item ->
                ListComponent(
                    title = item.name,
                    trailing = item.timestamp.getAsTime(),
                    onClick = { navigateToDetail(item.id) },
                    onHold = {
                        selectedItem = item.id
                        selectedName = item.name
                        isShowDialog = true
                    },
                    imageUrl = item.imageUrl,
                    modifier = modifier.animateItemPlacement()
                )
                Divider()
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
    }
    else EmptyView(message = stringResource(R.string.history_unavailable))
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    RasakuTheme { HistoryScreen({}) }
}