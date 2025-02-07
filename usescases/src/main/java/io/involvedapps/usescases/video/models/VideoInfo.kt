package io.involvedapps.usescases.video.models

import kotlin.random.Random

data class VideoInfo(
    val title: String,
    val thumbnailUrl: String,
    val videoUrl: String,
)

fun Random.nextVideoInfo() = VideoInfo(
    title = nextLong().toString(),
    thumbnailUrl = nextLong().toString(),
    videoUrl = nextLong().toString(),
)