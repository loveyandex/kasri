package com.amin.ui.web.controllers;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.jsons.FormInfo2;
import com.amin.scripting.Functions;
import com.amin.ui.web.OndayResponseEntity;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Label;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;

/**
 * is created by aMIN on 10/13/2018 at 2:36 AM
 */
//@CrossOrigin("http://localhost:4200")
@CrossOrigin("http://localhost:8080")
@RestController
public class MainController {
    @GetMapping("/car")
    public ArrayList god() {
        final ArrayList arrayList = new Gson().fromJson("['namegod','sdsd','sdsd']", ArrayList.class);
        return arrayList;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/onday")
    public ArrayList<OndayResponseEntity> onday() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        FormInfo formInfo = new FormInfo(new
                Date(10, 26, 1992)
                , "WIND_SPEED", "17240", "",
                "turkey", "12000", 1981, 2017, "m/s"
        );


        return Functions.getInstance().Api_onDayOneHeightOneStation(formInfo);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/ondaypost2")
    public ArrayList<OndayResponseEntity> ondaypost2(@RequestBody FormInfo formInfos, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        return Functions.getInstance().Api_onDayOneHeightOneStation(formInfos);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/ondaypost")
    public ArrayList<OndayResponseEntity> ondaypost(@RequestBody FormInfo2 formInfos, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        PersianCalendar persianCalendar = PersianCalendar.of(1372, formInfos.getMonth(), formInfos.getDay());

        PlainDate plainDate = persianCalendar.transform(PlainDate.class);


        FormInfo formInfo = new FormInfo(new Date(plainDate.getMonth(), plainDate.getDayOfMonth(), formInfos.getYear())
                , formInfos.getFeaureName(), formInfos.getStationNumber(), formInfos.getStationName(), formInfos.getCountry(),
                formInfos.getHeight(), formInfos.getLowerYear(), formInfos.getHighYear(), formInfos.getFeatureUnit());

        return Functions.getInstance().Api_onDayOneHeightOneStation(formInfo);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getcountry")
    public ArrayList<String> getCountry() {
        try {
            ArrayList<String> countriesName = Mapping.getFileLines(C.COUNTRIES_CONFIG_PATH);
            return countriesName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getStationsOfCountry/{cntry}")
    public ArrayList<String> getStationsOfCountry(@PathVariable String cntry) {
        Map<String, String> stationNumTOCities;
        try {
            stationNumTOCities = Mapping.
                    MapStationNumTOCities(C.STATES_PATH + "/" + cntry + ".conf.csv");

            final Collection<String> values = stationNumTOCities.values();
            List<String> lisst = new ArrayList<>(values);
            Collections.sort(lisst);

            final Set<String> statinss = stationNumTOCities.keySet();
            List<String> list = new ArrayList<String>(statinss);

            final ArrayList<String> asli = new ArrayList<>();
            for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                if (!station.getValue().equals("&"))
                {
                    System.out.println(station.getKey()+"..."+station.getValue());
                    asli.add(station.getKey());
                }
            }
            Collections.sort(asli);
            return asli;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/nametonumber/{cntry}/{stn}")
    public String stationNameToStationNumber(@PathVariable String cntry, @PathVariable String stn) {
        Map<String, String> stationNumTOCities;
        stn = stn.substring(0, stn.length() - 1).replaceAll("xxxyxxxyxxx", "/");
        try {
            stationNumTOCities = Mapping.
                    MapStationNumTOCities(C.STATES_PATH + "/" + cntry + ".conf.csv");
            for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                if (station.getKey().equals(stn)) {
                    return station.getValue();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }


}