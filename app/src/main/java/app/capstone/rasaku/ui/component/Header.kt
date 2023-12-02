package app.capstone.rasaku.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.capstone.rasaku.R
import app.capstone.rasaku.ui.theme.RasakuTheme
import coil.compose.AsyncImage

@Composable
fun Header(
    imageUrl: String,
    navigateToSearchInput: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder_48x48),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    // TODO : Navigate to SearchInput Screen
                    navigateToSearchInput()
                }
        ) {
            Text(
                text = stringResource(id = R.string.search_food),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = modifier.padding(horizontal = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    RasakuTheme {
        Header(
            imageUrl = "",
            navigateToSearchInput = {},
        )
    }
}