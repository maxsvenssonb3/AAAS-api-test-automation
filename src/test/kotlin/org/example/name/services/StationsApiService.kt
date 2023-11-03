package org.example.name.services

import io.restassured.module.jsv.JsonSchemaValidator
import org.apache.http.HttpStatus
import org.example.name.clients.StationsApiClient
import org.example.name.constants.DEFAULT_THRESHOLD_TIME_MILLISECONDS
import org.hamcrest.number.OrderingComparison
import java.util.concurrent.TimeUnit

class StationsApiService {
    private val stationsApiClient: StationsApiClient = StationsApiClient()

    /*
        GET - Stations/StationById/{id}
    */
    fun getStationByIdReturns200(id: String) {
        stationsApiClient.getStationById(id).then().statusCode(HttpStatus.SC_OK)
    }

    fun getStationByIdHasAValidSchema(id: String) {
        stationsApiClient.getStationById(id)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/Stations_StationById_{id}_get_200_schema.json"))
    }

    fun getStationByIdMeetsPerformanceThreshold(id: String) {
        stationsApiClient.getStationById(id)
            .then()
            .time(
                OrderingComparison.lessThan(DEFAULT_THRESHOLD_TIME_MILLISECONDS),
                TimeUnit.MILLISECONDS
            )
            .statusCode(HttpStatus.SC_BAD_REQUEST)
    }
}