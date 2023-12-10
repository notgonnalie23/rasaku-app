package app.capstone.rasaku.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import coil.compose.AsyncImage

@Composable
fun ListComponent(
    title: String,
    id: Long,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
) {
    val imageModifier = modifier
        .padding(start = 24.dp)
        .size(56.dp)

    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(end = 24.dp)
            )
        },
        leadingContent = {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    placeholder = painterResource(id = R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_placeholder),
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
            }
        },
        modifier = Modifier
            .clickable {
                onClick(id)
            }
    )
    Divider()
}