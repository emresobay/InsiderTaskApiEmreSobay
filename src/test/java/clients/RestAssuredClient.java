package clients;

import static io.restassured.RestAssured.given;

import helpers.PropertyManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public abstract class RestAssuredClient {

    PropertyManager propertyManager = new PropertyManager();
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .addHeader("api_key", propertyManager.getProperty("application.properties","apiKey"))
            .setUrlEncodingEnabled(false)
            .build();

    public RestAssuredClient(String baseUrl) {
        requestSpecification.baseUri(baseUrl);
    }

    public Response get(String path) {
        return given(requestSpecification).when().get(path);
    }

    public Response post(String path, Object body) {
        return given(requestSpecification).when().body(body).post(path);
    }

    public Response put(String path, Object body) {
        return given(requestSpecification).when().body(body).put(path);
    }

    public Response delete(String path) {
        return given(requestSpecification).when().delete(path);
    }
}
