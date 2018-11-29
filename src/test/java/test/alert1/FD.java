package test.alert1;


import com.amin.database.database.DatabaseHandler;
import com.amin.jsons.City;
import com.amin.jsons.Country;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * is created by aMIN on 10/15/2018 at 12:59 AM
 */
public class FD {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("F:\\apps\\jvm\\kasri\\config\\jsons\\cities.json"));
        JsonReader reader2 = new JsonReader(new FileReader("F:\\apps\\jvm\\kasri\\config\\jsons\\country-by-abbreviation.json"));
        City[] cities = gson.fromJson(reader, City[].class); // contains the whole reviews list
        Country[] countries = gson.fromJson(reader2, Country[].class); // contains the whole reviews list


        final Connection connection = DatabaseHandler.getInstance().getConnection();
        connection.createStatement().execute("delete  from city");
        final PreparedStatement statement = connection.prepareStatement("insert into  city  values (?,?,?,?,?);");

        for (int i = 0; i < cities.length; i++) {
            final String abbr = cities[i].getCountry();
            final String countryname = abrtoName(countries, abbr);

            statement.setString(1, countryname);
            statement.setString(2, cities[i].getName());
            statement.setString(3,abbr );
            statement.setDouble(4,cities[i].getLat() );
            statement.setDouble(5,cities[i].getLng() );
            System.out.println(gson.toJson(cities[i]));
            statement.executeUpdate();

        }

    }

    public static String abrtoName(Country[] countries, String abbr) {
        for (int i = 0; i < countries.length; i++) {
            final Country country = countries[i];
            final String abbreviation = country.getAbbreviation();
            try {

                if (abbreviation.equalsIgnoreCase(abbr))
                    return country.getCountry();
            }catch (NullPointerException e)
            {
                continue;
            }
        }

        return null;

    }
}
