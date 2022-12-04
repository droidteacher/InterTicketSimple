package hu.zsoltkiss.interticketsimple.di.module

import hu.zsoltkiss.interticketsimple.app.TheApp
import hu.zsoltkiss.interticketsimple.ui.countries.CountriesViewModel
import hu.zsoltkiss.interticketsimple.ui.countries.CountriesViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

val appModule = module {
    factory<CountriesViewModel> { CountriesViewModelImpl(get()) }
}

fun startKoinApp(application: TheApp) {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(application)
        modules(listOf(appModule, networkModule))
    }
}