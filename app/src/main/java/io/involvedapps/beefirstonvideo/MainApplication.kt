package io.involvedapps.beefirstonvideo

import android.app.Application
import io.involvedapps.beefirstonvideo.injection.repositoryModule
import io.involvedapps.beefirstonvideo.injection.usecaseModule
import io.involvedapps.beefirstonvideo.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                repositoryModule,
                usecaseModule,
                viewModelModule,
            )
        }
    }

}