package io.involvedapps.beefirstonvideo.injection

import io.involvedapps.usescases.video.VideoUsecase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val usecaseModule = module {
    singleOf(::VideoUsecase)
}
