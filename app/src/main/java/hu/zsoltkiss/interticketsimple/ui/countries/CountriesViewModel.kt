package hu.zsoltkiss.interticketsimple.ui.countries

import hu.zsoltkiss.interticketsimple.data.model.Country
import io.reactivex.rxjava3.core.Single

interface CountriesViewModel {
    val countries: Single<List<Country>>
}