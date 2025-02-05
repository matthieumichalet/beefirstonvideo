package io.involvedapps.beefirstonvideo.video.mapping

import io.involvedapps.beefirstonvideo.video.models.VideoInfoUI
import io.involvedapps.usescases.video.models.VideoInfo

internal fun VideoInfo.mapToUI() = VideoInfoUI(
    title = title,
    thumbnailUrl = thumbnailUrl,
    videoUrl = videoUrl,
)