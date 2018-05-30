package Template;

import com.google.gson.Gson;

import java.util.Properties;

public class Country {
    public String name;
    public String alpha2_code;
    public String alpha3_code;

    public static String getDefault()
    {
        PropertiesReader.load(System.getProperty("user.dir") + "/src/test/java/test-data/country.properties");
        Country country = new Country();
        country.name = PropertiesReader.get("name");
        country.alpha2_code = PropertiesReader.get("alpha2_code");
        country.alpha3_code = PropertiesReader.get("alpha3_code");

        String jsonString = new Gson().toJson(country);
        System.out.println("jsonString = " + jsonString);
        return jsonString;
    }

}
