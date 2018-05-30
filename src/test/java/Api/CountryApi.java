package Api;

import Template.Country;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CountryApi {

    final static String getCountries = "/all";
    final static String getSpecificCountry = "/iso2code";

    // Assuming the endpoint
    final static String postNewCountry = "/postCountry";

    public static Response getAllCountries()
    {
        return given()
                //.contentType("application/json")
                .log().all()
                .when()
                .get(getCountries);
    }

    public static Response getSpecificCountry(String countryCode)
    {
        return given()
                .log().all()
                .when()
                .get(String.format("%s/%s",getSpecificCountry, countryCode));
    }

    public static Response createNewCountry()
    {
        return given()
                .body(Country.getDefault())
                .log().all()
                .when()
                .post(postNewCountry);
    }


}
