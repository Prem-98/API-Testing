package files;

import io.restassured.path.json.JsonPath;

public class reusableFuntion {

    public static JsonPath Rawtojson(String response){

        JsonPath js=new JsonPath(response);
        return js;
    }
}
