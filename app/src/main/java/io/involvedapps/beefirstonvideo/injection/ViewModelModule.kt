package io.involvedapps.beefirstonvideo.injection

import io.involvedapps.beefirstonvideo.video.VideoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::VideoViewModel)
}