package com.example.sharesocial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesocial.model.VideoClip
import com.example.sharesocial.viewmodel.ShareSocialViewModel

@Composable
fun VideosScreen(viewModel: ShareSocialViewModel) {
    val videos by viewModel.videos.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(videos, key = { it.id }) { video ->
                VideoItemView(video = video)
            }
        }
    }
}

@Composable
fun VideoItemView(video: VideoClip) {
    var isLiked by remember { mutableStateOf(video.isLiked) }
    var likesCount by remember { mutableIntStateOf(video.likesCount) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(Color.DarkGray)
            .testTag("video_item_${video.id}")
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.PlayCircle,
                contentDescription = "Video Playing",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(80.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Trending Videos",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                IconButton(onClick = { /* Sound */ }) {
                    Icon(Icons.Default.MusicNote, contentDescription = "Sound", tint = Color.White)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(video.creatorName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(video.creatorName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(video.description, color = Color.White, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Audiotrack, contentDescription = "Audio", tint = Color.White, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(video.soundName, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            likesCount = if (isLiked) likesCount + 1 else likesCount - 1
                        },
                        modifier = Modifier.testTag("video_like_btn_${video.id}")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isLiked) Color.Red else Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                            Text("$likesCount", color = Color.White, fontSize = 12.sp)
                        }
                    }

                    IconButton(onClick = { /* Comments */ }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Comment, contentDescription = "Comment", tint = Color.White, modifier = Modifier.size(32.dp))
                            Text("${video.commentsCount}", color = Color.White, fontSize = 12.sp)
                        }
                    }

                    IconButton(onClick = { /* Share */ }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(32.dp))
                            Text("Share", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
