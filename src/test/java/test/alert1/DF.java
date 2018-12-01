package test.alert1;


import com.amin.jsons.City;
import com.amin.jsons.Country;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;

import static test.alert1.FD.abrtoName;

/**
 * is created by aMIN on 10/15/2018 at 12:59 AM
 */
public class DF {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        final JFrame jFrame = new JFrame();
        jFrame.setResizable(false); //Disable the Resize Button
        jFrame.setSize(1444,744);
        jFrame.getContentPane().setBackground(Color.BLACK);
        jFrame.setLocation(333,333);
        jFrame.setUndecorated(true);
        jFrame.setVisible(true);







        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(new File("../jsons/","cities.json")));
        JsonReader reader2 = new JsonReader(new FileReader("../jsons/country-by-abbreviation.json"));
        City[] cities = gson.fromJson(reader, City[].class); // contains the whole reviews list
        Country[] countries = gson.fromJson(reader2, Country[].class); // contains the whole reviews list

        for (int i = 0; i < cities.length; i++) {
            final City city = cities[i];
            final String abbr = city.getCountry();
            final String countryname = abrtoName(countries, abbr);
            if (countryname!=null && city.getName().equalsIgnoreCase("madrid") )
                System.err.println(gson.toJson(city)+",");
        }

    }

}
