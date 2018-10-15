package test.alert1;


import com.amin.database.Driver;
import com.amin.jsons.City;
import com.amin.jsons.Country;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static test.alert1.FD.abrtoName;

/**
 * is created by aMIN on 10/15/2018 at 12:59 AM
 */
public class DF {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(new File("config/jsons/","cities.json")));
        JsonReader reader2 = new JsonReader(new FileReader("F:\\apps\\jvm\\kasri\\config\\jsons\\country-by-abbreviation.json"));
        City[] cities = gson.fromJson(reader, City[].class); // contains the whole reviews list
        Country[] countries = gson.fromJson(reader2, Country[].class); // contains the whole reviews list

        for (int i = 0; i < cities.length; i++) {
            final City city = cities[i];
            final String abbr = city.getCountry();
            final String countryname = abrtoName(countries, abbr);
            if (countryname!=null && countryname.contains("Iran") && city.getName().toLowerCase().contains("fas") )
                System.err.println(gson.toJson(city)+",");
        }

    }

}
