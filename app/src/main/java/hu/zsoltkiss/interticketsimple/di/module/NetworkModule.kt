package hu.zsoltkiss.interticketsimple.di.module

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import hu.zsoltkiss.interticketsimple.BuildConfig
import hu.zsoltkiss.interticketsimple.data.remote.CountriesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideCountryApi(get()) }
}

fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .build()
} else OkHttpClient
    .Builder()
    .build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()


fun provideCountryApi(retrofit: Retrofit): CountriesApi = retrofit.create(CountriesApi::class.java)