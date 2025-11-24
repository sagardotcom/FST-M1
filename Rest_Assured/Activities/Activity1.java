package activities;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

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

public class Activity1 {

	
	//Declare request and response specification objects;
		RequestSpecification requestSpec;
		ResponseSpecification responseSpec;
		int petId;
		
		@BeforeClass //Setup Function
		public void setUp() {
			
			//Create the request specification
			// only set common items else will use everything specified, like body which is for only post will go with get and delete.
			requestSpec = new RequestSpecBuilder().
				setBaseUri("https://petstore.swagger.io/v2").
				addHeader("Content-Type", "application/json").build();
				//addPathParam("petId", 12).build();
			
			// create the response specification
			responseSpec = new ResponseSpecBuilder().
					expectStatusCode(200).
					expectBody("status", Matchers.equalTo("alive")).
					expectResponseTime(Matchers.lessThanOrEqualTo(5000L)).
					build();
			
		}
		
		
		// POST https://petstore.swagger.io/v2/pet
		@Test(priority = 1)
		public void postRequestTest() {
			
			Map<String,Object> reqBody = new HashMap<>();
			reqBody.put("id", 77777);
			reqBody.put("name", "Ross");
			reqBody.put("status", "alive");
			
			//Send the request, save the response
			Response response = RestAssured.given().
					spec(requestSpec). //Always takes the Request specification url in spec
					body(reqBody).log().all().
					when().post("/pet");				
			//Extract the pet id from the response
			
			this.petId = response.then().extract().path("id");

			//Assertions
			response.then().spec(responseSpec).log().all();
//			response.then().spec(responseSpec).log().status();
//			response.then().spec(responseSpec).log().headers();
//			response.then().spec(responseSpec).log().body();
			assertEquals(response.then().extract().path("name"), "Ross");
			assertEquals(petId,77777);
			assertEquals(response.then().extract().path("status"), "alive");
		}
		
		// GET https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 2)
		public void getRequestTest() {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> inside get");
			//Send the request, save the response
//			given().spec(requestSpec).pathParam("petId", this.petId).
//			when().get("/pet/{petId}").
//			then().spec(responseSpec);
			//Extract the response		
			ValidatableResponse response = RestAssured.given().
					spec(requestSpec).pathParam("petId", this.petId). //Always takes the Request specification url in spec
					log().all().
					when().get("/pet/{petId}").
					then().statusCode(200).spec(responseSpec);	
			
			//Assertions
			response.spec(responseSpec).log().all();
//			response.then().spec(responseSpec).log().status();
//			response.then().spec(responseSpec).log().headers();
//			response.then().spec(responseSpec).log().body();
			assertEquals(response.extract().path("name"), "Ross");
			Assert.assertEquals(petId, 77777);
			assertEquals(response.extract().path("status"), "alive");
		}
		// DELETE https://petstore.swagger.io/v2/pet/{petId}
		@Test(priority = 3)
		public void deleteRequestTest() {
			//Send the request, save the response
			given().spec(requestSpec).pathParam("petId", this.petId).
			when().delete("/pet/{petId}").
			then().statusCode(200).body("message", Matchers.equalTo(""+this.petId));
			//Extract the response
			//Assertions
			
		}


}
