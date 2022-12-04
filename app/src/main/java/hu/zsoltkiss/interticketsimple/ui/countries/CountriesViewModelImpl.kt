package hu.zsoltkiss.interticketsimple.ui.countries

import androidx.lifecycle.ViewModel
import hu.zsoltkiss.interticketsimple.data.model.Country
import hu.zsoltkiss.interticketsimple.data.remote.CountriesApi
import io.reactivex.rxjava3.core.Single

class CountriesViewModelImpl(api: CountriesApi) : ViewModel(),
    CountriesViewModel {

    override val countries: Single<List<Country>> = api.fetchEuropeanCountries()
            .map { dtos ->
                dtos.map { it.toModel() }
            }

}