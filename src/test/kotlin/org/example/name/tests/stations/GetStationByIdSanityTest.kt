package org.example.name.tests.stations

import org.example.name.BaseTest
import org.example.name.annotations.TestDataKeys
import org.example.name.services.StationsApiService
import org.testng.annotations.Test

class GetStationByIdSanityTest : BaseTest() {
    @Test(dataProvider = "JsonTestData")
    @TestDataKeys("stationId", "testId", "ghej")
    fun getStationByIdSanityTest(stationId: String, testId: String, ghej: String) {
        val hej = testId
        val yo = stationId
        val tsss = ghej.toBoolean()
        val stationsApiService = StationsApiService()
        stationsApiService.getStationByIdReturns200(stationId)
    }
}