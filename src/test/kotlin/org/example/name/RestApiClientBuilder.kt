package org.example.name

import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.example.name.constants.JWT
import java.util.function.Supplier

private const val CLIENT_ID = ""

fun requestSpecBuilder(jwtToken: String = JWT): Supplier<RequestSpecBuilder> = Supplier {
    RequestSpecBuilder()
        .setBaseUri("https://dev-service-layer-api.azurewebsites.net")
        .addHeader("authorization", "Bearer $jwtToken")
        .addHeader("Accept", "application/json")
        .addHeader("Client-Id", CLIENT_ID)
        .addHeader("Accept-Language", "sv")
        .addHeader("Client-Platform", "iOS")
        .addHeader("Client-Version", "1.0")
        .addFilter(RequestLoggingFilter())
        .addFilter(ResponseLoggingFilter())
}