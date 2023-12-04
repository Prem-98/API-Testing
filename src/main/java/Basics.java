import files.jsonText;
import files.reusableFuntion;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.SimpleTimeZone;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String arg[]){

        // POST
        // Given - give inputs
        //When  - hit the API(will give HTTP method,resourse)
        // Then - validate the API response
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(jsonText.Addplace()).when().post("maps/api/place/add/json")  // here the boday is placed in other java file in files folder and from there we are calling the json boday
                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        JsonPath js=new JsonPath(response); // for prasing the text into json
        String placeid=js.getString("place_id");
        System.out.println(placeid);

        // PUT here we are changing the address of the place we have added through POST
        String newplace="summar walk,Africa";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeid+"\",\n" +
                        "\"address\":\""+newplace+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").
                when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
        //GET
        String getResponse=given().queryParam("key","qaclick123").
                queryParam("place_id",placeid).
                when().get("maps/api/place/get/json").
                then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1= reusableFuntion.Rawtojson(getResponse);
        String actualAddress=js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress,newplace);

    }
}
