package io.involvedapps.repositories.video.mapping

import io.involvedapps.repositories.video.models.VideoInfoEntity
import io.involvedapps.usescases.video.models.VideoInfo

internal fun VideoInfoEntity.mapToDomain(): VideoInfo? {
    return if (qualities.qualities.isEmpty()) {
        null
    } else {
        VideoInfo(
            title = title,
            thumbnailUrl = thumbnails.thumbnail360Url,
            videoUrl = qualities.qualities.first().url
        )
    }
}
