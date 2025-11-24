package activities;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Activity3 {

	
	//Declare request and response specification objects;
		RequestSpecification requestSpec;
		ResponseSpecification responseSpec;
		
		
		@BeforeClass //Setup Function
		public void setUp() {
			
			//Create the request specification
			// only set common items else will use everything specified, like body which is for only post will go with get and delete.
			requestSpec = new RequestSpecBuilder().
				setBaseUri("https://petstore.swagger.io/v2/pet").
				addHeader("Content-Type", "application/json").build();
				//addPathParam("petId", 12).build();
			
			// create the response specification
			responseSpec = new ResponseSpecBuilder().
					expectStatusCode(200).
					expectContentType("application/json").
					expectBody("status", Matchers.equalTo("alive")).
					expectResponseTime(Matchers.lessThanOrEqualTo(5000L)).
					build();
			
		}
		
		@DataProvider(name = "petInfo")
		public Object[][] petInfoProvider() {
			// Setting parameters to pass to test case
			Object[][] testData = new Object[][] { 
				{ 77232, "Riley", "alive" }, 
				{ 77233, "Hansel", "alive" } 
			};
			return testData;
		}
		
		// POST https://petstore.swagger.io/v2/pet
		@Test(priority = 1, dataProvider = "petInfo")
		public void postRequestTest(Integer petId, String petName, String petStatus) {
			
			Map<String,Object> reqBody = new HashMap<>();
			reqBody.put("id", petId);
			reqBody.put("name", petName);
			reqBody.put("status", petStatus);
			
			//Send the request, save the response
			ValidatableResponse response = RestAssured.given().
					spec(requestSpec). //Always takes the Request specification url in spec
					body(reqBody).log().all().
					when().post().
					then().spec(responseSpec) // Assertions using responseSpec
					.body("name", equalTo(petName)); // Additional Assertion;				
			//Extract the pet id from the response
			
			//Assertions
			response.spec(responseSpec).log().all();
			}
		
		
		// GET https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 2, dataProvider = "petInfo")
		public void getRequestTest(Integer petId, String petName, String petStatus) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> inside get");
			//Extract the response		
			ValidatableResponse response = RestAssured.given().
					spec(requestSpec).pathParam("petId", petId). //Always takes the Request specification url in spec
					log().all().
					when().get("/{petId}").
					then().statusCode(200).spec(responseSpec);	
			
			//Assertions
			response.spec(responseSpec).log().all();
			assertEquals(response.extract().path("name"), petName);
			//======================> why this is failing 
			assertEquals(response.extract().path("id"), petId);
			assertEquals(response.extract().path("status"), petStatus);
		}
		
		
		
		
		
		
		
		// DELETE https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 3,dataProvider = "petInfo")
		public void deleteRequestTest(Integer petId, String petName, String petStatus) {
			//Send the request, save the response
			given().spec(requestSpec).pathParam("petId", petId).
			when().delete("/{petId}").
			then().statusCode(200).body("message", Matchers.equalTo(""+petId));
			//Extract the response
			//Assertions
			
		}


}
