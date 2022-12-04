package hu.zsoltkiss.interticketsimple.resources

import hu.zsoltkiss.interticketsimple.data.remote.CountryDto

object UnitTestData {
    val dtos = listOf(
        CountryDto("Country 1", "Capital 1", "REG 1", "subreg 1", population = 100, area = 5000.0),
        CountryDto("Country 2", "Capital 2", "REG 1", "subreg 1", population = 120, area = 4850.0),
        CountryDto("Magyarország", "Budapest", "Európa", "Közép-Európa", population = 10000000, area = 93000.0),
    )
}