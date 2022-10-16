package Calendarific;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Base {

    // create constants for country = US and year = 2022

    private static final String API_KEY = "cffcfa66c03af4127faf3d1fb3454ac8b65a4526";

    private static final String BASE_URI = "https://calendarific.com/api/v2";

    private static final String HOLIDAY_ENPOINT = "/holidays";
    private static final String LANGUAGES_ENDPOINT = "/languages";
    private static final String COUNTRIES_ENPOINT = "/countries";

    private static JsonPath jsonPath;


    public static Map<String , String> getQueryParams(String country , String year) {
        Map<String , String> queryParams = new HashMap<>();
        queryParams.put("api_key" , API_KEY);
        queryParams.put("country" , country);
        queryParams.put("year" , year);
        return queryParams;
    }

    public static Response getAllHolidays(Map<String , String> queryParams) {
        baseURI = BASE_URI;
        basePath = HOLIDAY_ENPOINT;
        return given()
                .queryParams(getQueryParams("US" , "2022"))
                .get();
    }

    public static Response getAllLanguages() {
        baseURI = BASE_URI;
        basePath = LANGUAGES_ENDPOINT;
        return given()
                .queryParam("api_key" , API_KEY)
                .get();
    }

    public static Response getAllCountries() {
        baseURI = BASE_URI;
        basePath = COUNTRIES_ENPOINT;
        return given()
                .queryParam("api_key" , API_KEY)
                .get();
    }

    public static List<Object> listFromJsonPath(Response response , String path) {
        jsonPath = response.jsonPath();
        return jsonPath.getList(path);
    }
}
