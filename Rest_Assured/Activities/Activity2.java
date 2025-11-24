package activities;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {

	
	//Declare request and response specification objects;
		RequestSpecification requestSpec;
		ResponseSpecification responseSpec;
		String userId;
		
		@BeforeClass //Setup Function
		public void setUp() {
			
			//Create the request specification
			// only set common items else will use everything specified, like body which is for only post will go with get and delete.
			requestSpec = new RequestSpecBuilder().
				setBaseUri("https://petstore.swagger.io/v2/user").
				addHeader("Content-Type", "application/json").build();
				//addPathParam("petId", 12).build();
			
			// create the response specification
			responseSpec = new ResponseSpecBuilder().
					expectStatusCode(200).
					expectBody("message", Matchers.equalTo("9901")).
					expectResponseTime(Matchers.lessThanOrEqualTo(5000L)).
					build();
			
		}
		
		
		// POST https://petstore.swagger.io/v2/pet
		@Test(priority = 1)
		public void postRequestTest() throws IOException {
			FileInputStream userInfo = new FileInputStream("src/test/resources/userInfo.json"); 
				
			//Send the request, save the response
			Response response = RestAssured.given().
					spec(requestSpec). //Always takes the Request specification url in spec
					body(userInfo).log().all().
					when().post();				
			userInfo.close();
			response.then().log().all();			
			//Extract the pet id from the response
			//============================================= why unable to extract the id?
			userId = response.then().extract().path("message");
			
			System.out.println(userId);
			//Assertions
			response.then().spec(responseSpec).log().all();
			response.then().body("code", equalTo(200));
			response.then().body("message", equalTo("9901"));

			
			//System.out.println(petId = response.then().extract().path("id"));
		}
		
		
		
		
		// GET https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 2)
		public void getRequestTest() {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> inside get");
			File outputJSON = new File("src/test/java/activities/userGETResponse.json");
			//Send the request, save the response
			//Extract the response		
			Response response = RestAssured.given().
					spec(requestSpec).pathParam("username", "justinc"). //Always takes the Request specification url in spec
					log().all().
					when().get("/{username}");
				//	then().statusCode(200).spec(responseSpec);	
			//===================================> y this line error
			

			// Get response body
			String resBody = response.getBody().asPrettyString();
	 
			try {
				// Create JSON file
				outputJSON.createNewFile();
				// Write response body to external file
				FileWriter writer = new FileWriter(outputJSON.getPath());
				writer.write(resBody);
				writer.close();
			} catch (IOException excp) {
				excp.printStackTrace();
			}
			
			// Assertion
			response.then().body("id", equalTo(9901));
			response.then().body("username", equalTo("justinc"));
			response.then().body("firstName", equalTo("Justin"));
			response.then().body("lastName", equalTo("Case"));
			response.then().body("email", equalTo("justincase@mail.com"));
			response.then().body("password", equalTo("password123"));
			response.then().body("phone", equalTo("9812763450"));
		
		}
		
		// DELETE https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 3)
		public void deleteRequestTest() {
			//Send the request, save the response
			given().spec(requestSpec).pathParam("username", "justinc").log().all().
			when().delete("/{username}").
			then().statusCode(200).body("message", Matchers.equalTo("justinc"));
			//Extract the response
			//Assertions

			// Assertion
//			response.then().body("code", equalTo(200));
//			response.then().body("message", equalTo("justinc"));
			
		}

	
	
}
