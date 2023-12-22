package app.capstone.rasaku.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListComponent(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onHold: () -> Unit = {},
    imageUrl: String? = null,
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(end = 24.dp)
            )
        },
        leadingContent = {
            AsyncImage(
                model = imageUrl ?: "https://i.ibb.co/X7H93zg/img-placeholder.jpg",
                contentDescription = title,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(start = 24.dp)
                    .size(56.dp)
            )
        },
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onHold,
        ),
    )
    Divider()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListComponent(
    title: String,
    trailing: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onHold: () -> Unit = {},
    imageUrl: String? = null,
) {
    ListItem(headlineContent = {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
        )
    }, leadingContent = {
            AsyncImage(
                model = imageUrl ?: "https://i.ibb.co/X7H93zg/img-placeholder.jpg",
                contentDescription = title,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(start = 24.dp)
                    .size(56.dp)
            )
    }, trailingContent = {
        Text(
            text = trailing,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.padding(end = 16.dp)
        )
    }, modifier = Modifier.combinedClickable(
        onClick = onClick, onLongClick = onHold
    ),
    )
    Divider()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListComponent(
    title: String,
    body: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    onHold: () -> Unit = {},
) {
    ListItem(headlineContent = {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(end = 24.dp)
        )
    }, supportingContent = {
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
        )
    }, leadingContent = {
        AsyncImage(
            model = imageUrl ?: "https://i.ibb.co/X7H93zg/img-placeholder.jpg",
            contentDescription = title,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(start = 24.dp)
                .size(56.dp)
        )
    }, modifier = Modifier.combinedClickable(
        onClick = onClick, onLongClick = onHold
    ),
    )
    Divider()
}

@Preview(showBackground = true)
@Composable
private fun ListComponentPreview() {
    RasakuTheme {
        Column {
            ListComponent(title = "Soto Lamongan", onClick = {})
            ListComponent(title = "Snack Favorite", onClick = {}, body = "5 menu")
            ListComponent(title = "Soto Lamongan", onClick = {}, trailing = "05.00")
        }
    }
}