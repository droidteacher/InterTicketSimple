package hu.zsoltkiss.interticketsimple.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("/v2/region/europe")
    fun fetchEuropeanCountries(): Single<List<CountryDto>>
}