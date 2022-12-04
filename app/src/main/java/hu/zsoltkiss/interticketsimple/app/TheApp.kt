package hu.zsoltkiss.interticketsimple.app

import android.app.Application
import hu.zsoltkiss.interticketsimple.di.module.startKoinApp

class TheApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoinApp(this)
    }

}