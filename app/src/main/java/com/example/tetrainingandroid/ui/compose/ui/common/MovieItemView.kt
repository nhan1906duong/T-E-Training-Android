package com.example.tetrainingandroid.ui.compose.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.model.ImageConfiguration
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.compose.theme.SecondaryColor

@Composable
fun MovieItemView(movie: Movie, onClick: (Int) -> Unit) {
    Column(modifier = Modifier
        .width(128.dp)
        .clickable(onClick = {
            onClick(movie.id ?: 0)
        })) {
        AsyncImage(
            model = MovieHelper.getUrl(movie.posterPath, ImageConfiguration.Size.POSTER),
            contentDescription = "",
            modifier = Modifier
                .width(128.dp)
                .aspectRatio(2f / 3)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title ?: stringResource(id = R.string.unknown),
            style = MaterialTheme.typography.subtitle2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.releaseDate ?: stringResource(id = R.string.unknown),
            style = MaterialTheme.typography.body2,
            maxLines = 1
        )
    }
}

@Composable
fun MovieSectionView(@StringRes title: Int, movies: List<Movie>, onClick: (Int) -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = title),
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.subtitle1.copy(
                color = SecondaryColor,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            items(movies) { movie ->
                MovieItemView(movie, onClick)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

object MovieHelper {
    fun getUrl(path: String?, size: ImageConfiguration.Size): String {
        val newPath: String = if (path?.startsWith("/") == true) {
            path.substring(1)
        } else {
            path ?: ""
        }
        return listOf(Config.BASE_IMAGE_URL, size.size, newPath).joinToString(separator = "/")
    }
}