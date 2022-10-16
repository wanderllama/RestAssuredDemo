package Calendarific;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import Utility.Driver;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.time.Month;
import java.util.Map;

public class TestCases extends Base {

    @Test(testName = "GET / print all holidays by year&region")
    public void test01() {
        getAllHolidays(getQueryParams("US" , "2022")).prettyPrint();
    }

    @Test(testName = "GET / print all languages")
    public void test02() {
        getAllLanguages().prettyPrint();
    }

    @Test(testName = "GET / print all countries")
    public void test03() {
        getAllCountries().prettyPrint();
    }

    @Test(testName = "GET / endpoints status code 200")
    public void test04() {
        getAllHolidays(getQueryParams("US" , "2022"))
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(testName = "GET / languages enpoint status code 200")
    public void test05() {
        getAllLanguages().then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(testName = "GET / countries endpoint status code 200")
    public void test06() {
        getAllCountries().then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(testName = "GET / holiday json schema validation")
    public void test07() {
        getAllHolidays(getQueryParams("US" , "2022"))
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/Calendarific/resources/HolidaySchema.json")));
    }

    @Test(testName = "GET / languages json schema validations")
    public void test08() {
        getAllLanguages().then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/Calendarific/resources/LanguagesSchema.json")));
    }

    @Test(testName = "GET / countries json schema validation")
    public void test09() {
        getAllCountries().then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/Calendarific/resources/CountriesSchema.json")));
    }

    @Test(testName = "GET / validate number of US holidays")
    public void test10() {
        Assert.assertEquals(listFromJsonPath(getAllHolidays(getQueryParams("US" , "2022")) , "response.holidays.name").size() , 648);
    }

    @Test(testName = "GET / verify date of holiday from google search using selenium")
    public void test00() {
        Driver.getDriver().get("https://www.google.com/");

        String holidaySearch = "World Creativity and Innovation Day";

        // search for the holiday
        Driver.getDriver().findElement(By.name("q")).sendKeys(holidaySearch);
        Driver.getDriver().findElement(By.name("btnK")).click();

        Driver.quitDriver();

        // parse the holidays date from the webpage
        String expectedDate = Driver.getDriver().findElement(By.xpath("//div[@data-tts='answers']/div")).getText();
        int expectedDay = Integer.parseInt(expectedDate.substring(0 , expectedDate.indexOf(' ')));
        Month expectedMonth = Month.valueOf(expectedDate.substring(expectedDate.indexOf(' ') + 1).toUpperCase());

        // create list of holidays and list of their dates from the api response
        Response response = getAllHolidays(getQueryParams("US" , "2022"));
        List<Object> holidayNames = listFromJsonPath(response , "response.holidays.name");
        List<Object> dates = listFromJsonPath(response , "response.holidays.date.datetime");

        // find the holiday and its respective date and then assert against the selenium results
        for (int i = 0; i < holidayNames.size(); i++) {
            if (holidayNames.get(i).equals(holidaySearch)) {
                Map<String , Integer> dateMap = (Map<String, Integer>) dates.get(i);
                Assert.assertEquals(dateMap.get("month") , expectedMonth.getValue());
                Assert.assertEquals(dateMap.get("day") , expectedDay);
            }
        }
    }

    // find which country has the most holidays and the lease

    // sort countries by number of holidays

    // sort countries by number of supported languages

}
