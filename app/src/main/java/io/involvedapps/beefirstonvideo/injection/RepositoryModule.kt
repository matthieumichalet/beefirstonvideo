package io.involvedapps.beefirstonvideo.injection

import io.involvedapps.repositories.video.VideoRepositoryImpl
import io.involvedapps.repositories.video.VideoRepositoryRemote
import io.involvedapps.usescases.video.VideoRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::VideoRepositoryImpl) { bind<VideoRepository>() }
    singleOf(::VideoRepositoryRemote)
}
