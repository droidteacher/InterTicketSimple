package hu.zsoltkiss.interticketsimple.data.remote

import com.google.gson.annotations.SerializedName
import hu.zsoltkiss.interticketsimple.data.model.Country

data class CountryDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("capital")
    val capital: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("subregion")
    val subregion: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("area")
    val area: Double
) {
    fun toModel() = Country(name, capital)
}