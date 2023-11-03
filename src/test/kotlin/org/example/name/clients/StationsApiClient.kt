package org.example.name.clients

import io.restassured.response.Response
import org.example.name.requestSpecBuilder

class StationsApiClient {

    private val stationsApi: StationsApi = StationsApi.stations(requestSpecBuilder())
    fun getStationById(id: String): Response {
        return stationsApi.stationsStationByIdIdGet()
            .idPath(id)
            .execute { it }
    }

    fun getStationsByGeoLocation(latitude: Double, longitude: Double): Response {
        return stationsApi.stationsGeolocationLatitudeLongitudeGet()
            .latitudePath(latitude)
            .longitudePath(longitude)
            .execute { it }
    }

    fun getStationsByGeoLocationAndRadius(latitude: Double, longitude: Double, radius: Double): Response {
        return stationsApi.stationsGeolocationLatitudeLongitudeGet()
            .latitudePath(latitude)
            .longitudePath(longitude)
            .radiusQuery(radius)
            .execute { it }
    }

    fun getStationsBySearchQuery(searchText: String): Response {
        return stationsApi.stationsSearchQueryGet()
            .queryPath(searchText)
            .execute { it }
    }

    fun getStationsBySearchQueryAndOptionalParameters(
        queryText: String,
        page: String,
        itemsPerPage: String
    ): Response {
        return stationsApi.stationsSearchQueryGet()
            .queryPath(queryText)
            .pageQuery(page)
            .itemsPerPageQuery(itemsPerPage)
            .execute { it }
    }
}

