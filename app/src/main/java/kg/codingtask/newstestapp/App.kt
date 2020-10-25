package kg.codingtask.newstestapp

import android.app.Application
import kg.codingtask.newstestapp.di.dataModule
import kg.codingtask.newstestapp.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, dataModule))}
    }
}