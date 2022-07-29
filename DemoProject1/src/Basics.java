
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {
		
		// Validate if add place API is working as expected

		//Add place -->Update place with New Address -->Get Place to validate if New address is present in response
		
		// given - all input details 
		//when - Submit the API- resource, http method
		//then - validate the response
		
		RestAssured.baseURI =  "https://rahulshettyacademy.com";
	    String response =  given().log().all().queryParam("key","qaclick123").headers("Content-Type","application/json").
	    body(payload.AddPlace()).when().post("maps/api/place/add/json").
	    then().statusCode(200).body("scope", equalTo("APP")).
	    header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
	    
	    System.out.println(response);
	    
	    //Parsing The Response body using JsonPath
	    
	    JsonPath js = ReusableMethods.rawToJson(response);
	    String placeid =  js.getString("place_id");
	    System.out.println("Place Id : " + placeid);
	    
	    //Update place
	    String newAddress = "Lalbag road, Bangalore";
	    
	    given().log().all().queryParam("key","qaclick123").headers("Content-Type","application/json").
	    body("{\r\n"
	    		+ "\"place_id\":\""+placeid+"\",\r\n"
	    		+ "\"address\":\""+newAddress+"\",\r\n"
	    		+ "\"key\":\"qaclick123\"\r\n"
	    		+ "}").
	    when().put("maps/api/place/update/json").
	    then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
	    
	    //Get Place
	    
	    String getplaceResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeid).
	    when().get("maps/api/place/get/json").
	    then().assertThat().log().all().statusCode(200).extract().response().asString();
	    
	    JsonPath js1 = ReusableMethods.rawToJson(getplaceResponse);
	    String actualAddress =  js1.getString("address");
	    
	    System.out.println("Updated Address : " + actualAddress);
	    Assert.assertEquals(actualAddress, "Lalbag road, Bangalore");
	    
	    
	    
	}
}




































