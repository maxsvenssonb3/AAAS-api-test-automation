package org.example.name

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.example.name.annotations.TestDataKeys
import org.testng.annotations.DataProvider
import java.io.FileNotFoundException
import java.io.FileReader
import java.lang.reflect.Method

abstract class BaseTest {
    private val testDataFile: String
        get() = "src/test/resources/testdata/${this.javaClass.simpleName}.json"

    @DataProvider(name = "JsonTestData", parallel = false)
    protected fun loadTestDataFromJson(method: Method?): Array<Array<Any>> {
        try {
            val jsonElement = JsonParser.parseReader(FileReader(testDataFile))
            val jsonArray = jsonElement.asJsonObject.getAsJsonArray("testData")
            return TestDataLoader.loadData(jsonArray, method)
        } catch (e: FileNotFoundException) {
            throw RuntimeException("Test data file not found: $testDataFile", e)
        }
    }
}

private object TestDataLoader {
    fun loadData(
        jsonArray: JsonArray,
        method: Method?
    ): Array<Array<Any>> {
        val dataList = ArrayList<Array<Any>>()
        jsonArray.forEach { jsonObj ->
            val jsonObject = jsonObj.asJsonObject
            if (jsonObject.get("isTestActive").asBoolean) {
                addTestData(dataList, method, jsonObject)
            }
        }
        return dataList.toTypedArray()
    }

    private fun addTestData(dataList: ArrayList<Array<Any>>, method: Method?, jsonObject: JsonObject) {
        try {
            dataList.add(
                method?.getAnnotation(TestDataKeys::class.java)?.value?.map { key ->
                    jsonObject.get(key)?.asString
                        ?: throw IllegalArgumentException("Missing data for key: $key")
                }?.toTypedArray()
                    ?: throw IllegalArgumentException("Missing TestDataKeys annotation on method: $method")
            )
        } catch (e: Exception) {
            throw RuntimeException("Error while processing test data", e)
        }
    }
}