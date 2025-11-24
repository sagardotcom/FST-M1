package examples;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // Set the headers
    Map<String, String> headers = new HashMap<>();

    // Create the Fragment for POST request
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public V4Pact createPostFragment(PactDslWithProvider builder) {
        // Set headers
        headers.put("Content-Type", "application/json");
        // Create the JSON body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Saahil")
                .stringType("lastName", "Sharma")
                .stringType("email", "saahil@example.com");
        // Create the contract(Pact)
        return builder.given("POST Request")
            .uponReceiving("A request to create a user")
                .method("POST")
                .path("/api/users")
                .headers(headers)
                .body(reqResBody)
            .willRespondWith()
                .status(201)
                .body(reqResBody)
            .toPact(V4Pact.class);
    }

    // Create the Fragment for Get request
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public V4Pact createGetFragment(PactDslWithProvider builder) {
        // Set headers
        headers.put("Content-Type", "application/json");
        // Create the JSON body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id", 1)
                .stringType("firstName", "Saahil")
                .stringType("lastName", "Sharma")
                .stringType("email", "saahil@example.com");
        // Create the contract(Pact)
        return builder.given("GET Request")
                .uponReceiving("A request to get a user")
                .method("GET")
                .path("/api/users")
                .headers(headers)
                .pathFromProviderState("/api/users/{id}", "/api/users/1")
                .willRespondWith()
                .status(200)
                .body(reqResBody)
                .toPact(V4Pact.class);
    }

    // Create the Fragment for Get request
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public V4Pact createGetAllFragment(PactDslWithProvider builder) {
        // Set headers
        headers.put("Content-Type", "application/json");
        // Create the JSON body
        DslPart arrResBody = PactDslJsonArray.arrayMaxLike(2)
                .numberType("id", 1)
                .stringType("firstName", "Gretel")
                .stringType("lastName", "")
                .stringType("email", "gretel@example.com")
                .numberType("id", 2)
                .stringType("firstName", "Hansel")
                .stringType("lastName", "")
                .stringType("email", "hansel@example.com");

        // Create the contract(Pact)
        return builder.given("GET ALL Request")
                .uponReceiving("A request to get all users")
                .method("GET")
                .path("/api/users")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(arrResBody)
                .toPact(V4Pact.class);
    }

    // Create the Fragment for Get request
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public V4Pact createDeleteFragment(PactDslWithProvider builder) {
        // Set headers
        headers.put("Content-Type", "application/json");
        // Create the contract(Pact)
        return builder.given("DELETE Request")
                .uponReceiving("A request to delete a user")
                .method("DELETE")
                .path("/api/users")
                .headers(headers)
                .pathFromProviderState("/api/users/{id}", "/api/users/1")
                .willRespondWith()
                .status(204)
                .toPact(V4Pact.class);
    }

    // Consumer test with mock provider
    @Test
    @PactTestFor(providerName = "UserProvider", pactMethod = "createPostFragment")
    public void postRequestTest(MockServer mockServer) {
        // Create a request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 123);
        reqBody.put("firstName", "Saahil");
        reqBody.put("lastName", "Sharma");
        reqBody.put("email", "saahil@example.com");

        // Send request, get response, assert response
        given().baseUri(mockServer.getUrl() + "/api/users").headers(headers).body(reqBody).log().all().
        when().post().
        then().log().all().statusCode(201).body("email", equalTo("saahil@example.com"));
    }

    // Consumer test with mock provider
    @Test
    @PactTestFor(providerName = "UserProvider", pactMethod = "createGetFragment")
    public void getRequestTest(MockServer mockServer) {
        // Send request, get response, assert response
        given().baseUri(mockServer.getUrl() + "/api/users").headers(headers).log().all().
                when().get("/1").
                then().statusCode(200).log().all();
    }

    // Consumer test with mock provider
    @Test
    @PactTestFor(providerName = "UserProvider", pactMethod = "createDeleteFragment")
    public void deleteRequestTest(MockServer mockServer) {
        // Send request, get response, assert response
        given().baseUri(mockServer.getUrl() + "/api/users").headers(headers).log().all().
                when().delete("/1").
                then().statusCode(204).log().all();
    }

    // Consumer test with mock provider
    @Test
    @PactTestFor(providerName = "UserProvider", pactMethod = "createGetAllFragment")
    public void getAllRequestTest(MockServer mockServer) {
        // Send request, get response, assert response
        given().baseUri(mockServer.getUrl() + "/api/users").headers(headers).log().all().
                when().get().
                then().statusCode(200).log().all();
    }
}