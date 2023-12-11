package app.capstone.rasaku.ui.screen.favoritelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.capstone.rasaku.ui.ViewModelFactory
import app.capstone.rasaku.ui.component.ListComponent
import app.capstone.rasaku.ui.component.ModalDialog
import app.capstone.rasaku.ui.theme.RasakuTheme

@Composable
fun FavoriteListScreen(
    id: Long,
    navigateToBack: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: FavoriteListViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
    FavoriteListContent(
        id = id,
        delete = {
            viewModel.deleteFavorite(it)
            navigateToBack()
        },
        navigateToDetail = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
private fun FavoriteListContent(
    id: Long,
    delete: (Long) -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowDialog by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(12) { index ->
                ListComponent(
                    title = "Food #$index",
                    imageUrl = "https://placehold.co/56x56/png",
                    onClick = {
                        // TODO : Ke halaman detail
                        navigateToDetail(index.toLong())
                    }
                )
            }
        }
        FloatingActionButton(
            onClick = {
                isShowDialog = true
            },
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null
            )
        }
        if (isShowDialog)
            ModalDialog(
                title = "Hapus Koleksi Makanan",
                body = "Apakah anda yakin ingin menghapus koleksi ini?",
                positive = "Hapus",
                negative = "Batalkan",
                onPositiveClick = {
                    delete(id)
                    isShowDialog = false
                },
                onNegativeClick = {
                    isShowDialog = false
                },
                setShowDialog = {
                    isShowDialog = it
                }
            )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteListScreenPreview() {
    RasakuTheme { FavoriteListScreen(0, {}, {}) }
}