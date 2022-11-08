package org.example;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.util.Properties;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(SerenityJUnit5Extension.class)
public class BaseSetup {

    Faker generator = new Faker();

    @BeforeAll
    public static void setup() throws IOException {

        var fileName = System.getProperty("env") + "_env.properties";

        var builder = new RequestSpecBuilder();
        var file = builder.getClass().getResourceAsStream("/" + fileName);

        var configuration = new Properties();
        configuration.load(file);

        var reqSpec = builder
                .setBaseUri(configuration.getProperty("todoist_url"))
                .setBasePath(configuration.getProperty("todoist_basePath"))
                .addHeader("Authorization", "Bearer " + configuration.getProperty("todoist_token"))
//               .log(LogDetail.ALL)
                .build();
        RestAssured.requestSpecification = reqSpec;
//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//        .log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // logi jeśli asercje sfailują
    }
}
