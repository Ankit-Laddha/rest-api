package TestScenarios;

import Api.CountryApi;
import Template.Country;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


public class TestCountry {

    final String baseURI = "http://services.groupkt.com/country/get";
    Response response;

    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = baseURI;
    }

    @DataProvider(name="CountryCodeProvider")
    public Object[][]getDataFromDataProvider(){
        return new Object[][] {
                { "US" },
                { "GB" },
                { "DE" }
        };
    }

    @Test(dataProvider="CountryCodeProvider")
    public void testGetCountry_Individually(String countryCode) {
        response = CountryApi.getAllCountries();
        response.then()
                .statusCode(200);

        List<Country> countryList = response.jsonPath().getList("RestResponse.result", Country.class);
        System.out.println("countryList.size() = " + countryList.size());
        assertThat(countryList.stream().map(p -> p.alpha2_code).collect(Collectors.toList())).contains(countryCode);
    }

    @Test
    public void testGetAllCountry_atOnce() {
        response = CountryApi.getAllCountries();
        response.then()
                .statusCode(200);

        List<String> listOfCountryCodes = new ArrayList<>();
        listOfCountryCodes.add("GB");
        listOfCountryCodes.add("DE");
        listOfCountryCodes.add("US");

        List<String> responseCountryList = response.jsonPath().getList("RestResponse.result.alpha2_code");
        listOfCountryCodes.stream()
                .forEach(p -> assertThat(responseCountryList)
                        .as("Country Code not present: " + p)
                        .contains(p)
                );
    }

    @Test
    public void testGetSpecificCountry_Invalid_XYZ() {
        String inputCountryCode = "XYZ";
        response = CountryApi.getSpecificCountry(inputCountryCode);
        response.then()
                .statusCode(200);

        List<String> errorMsg = response.jsonPath().getList("RestResponse.messages");
        List<Country> country = response.jsonPath().getList("RestResponse.result", Country.class);

        assertThat(errorMsg).as("Error msg is incorrect").contains(String.format("No matching country found for requested code [%s].",inputCountryCode));
        assertThat(country).as("Result key should not be present").isEmpty();
    }

    @Test
    public void testPostCountry() {
        response = CountryApi.createNewCountry();
        response.then()
                .statusCode(201);
    }

}
