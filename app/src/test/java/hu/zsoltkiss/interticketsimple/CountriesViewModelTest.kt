package hu.zsoltkiss.interticketsimple

import hu.zsoltkiss.interticketsimple.data.model.Country
import hu.zsoltkiss.interticketsimple.data.remote.CountriesApi
import hu.zsoltkiss.interticketsimple.di.module.appModule
import hu.zsoltkiss.interticketsimple.resources.UnitTestData
import hu.zsoltkiss.interticketsimple.ui.countries.CountriesViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get


class CountriesViewModelTest: KoinTest {

    private val apiMock = mockk<CountriesApi> {
        every { fetchEuropeanCountries() } returns Single.just(UnitTestData.dtos)
    }

    private val testNetworkModule = module {
        single { apiMock }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(listOf(appModule, testNetworkModule))
    }

    private lateinit var sut: CountriesViewModel

    @Before
    fun setUp() {
       sut = get<CountriesViewModel>()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `GIVEN view model initialized WHEN observer subscribes on countries observable THEN it fetches country list through API call`() {
        val expectedCountries = listOf(
            Country("Country 1", "Capital 1"),
            Country("Country 2", "Capital 2"),
            Country("Magyarország", "Budapest"),
        )

        val testObserver = TestObserver<List<Country>>()

        sut.countries.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValues(expectedCountries)

        verify(exactly = 1) { apiMock.fetchEuropeanCountries() }
    }
}