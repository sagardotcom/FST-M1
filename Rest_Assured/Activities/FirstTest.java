package examples;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FirstTest {
	
	// GET https://petstore.swagger.io/v2/pet/findByStatus?status=sold
	@Test
	public void getRequestWithQueryParam() {
		//Send Request and Save the response
		Response response = RestAssured.given().
				//Request Spec, allof the below is a single line.
				baseUri("https://petstore.swagger.io/v2/pet").
				headers("Content-Type","application/json").
				//header("Accept","application/json").
				queryParam("status", "sold").
		
		//Send Request and receive response
		when().get("/findByStatus");
		
		//Get the response Status code
		System.out.println("Status Code: "+response.getStatusCode());
		//Get the response Header
		System.out.println("Header code: \n"+response.getHeaders());
		// To use later we can use ==> System.out.println("Header code: \n"+response.getHeaders().asList());
		//Get The response body
		System.out.println("Body: \n"+response.getBody().asPrettyString());//cant print json object, need to convert to string first
		
		//Extract json Properties using jsonpath
		String petStatus= response.then().extract().path("[0].status");
		
		//Assertions
		Assert.assertEquals(petStatus, "sold");
		
		response.then().
		//Response spec
		statusCode(200).
		body("[0].status", Matchers.equalTo("sold"));
		
		
	}
	
	// GET https://petstore.swagger.io/v2/pet/{petId}
	@Test
	public void getRequestWithPathParam() {
		// Send request, save the response
		RestAssured.given().
		// Request spec
			baseUri("https://petstore.swagger.io/v2").
			header("Content-Type", "application/json").
			pathParam("petId", 12).
		// Send request and receive response
		when().get("/pet/{petId}").
		then().
			// Response spec
			statusCode(200).
			body("id", Matchers.equalTo(12));
	}

}
