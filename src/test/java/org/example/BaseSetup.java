package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SerenityJUnit5Extension.class)
public class BaseSetup {

    @BeforeAll
    public static void setup() {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://api.todoist.com")
                .setBasePath("/rest/v2")
                .addHeader("Authorization", "Bearer 3d805d60a854c15ef18a760c629963b5030fd4a2")
//               .log(LogDetail.ALL)
                .build();
        RestAssured.requestSpecification = builder.build();

//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//        .log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // logi jeśli asercje sfailują
    }
}
