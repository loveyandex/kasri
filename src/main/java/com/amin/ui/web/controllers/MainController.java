package com.amin.ui.web.controllers;

import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.scripting.Functions;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * is created by aMIN on 10/13/2018 at 2:36 AM
 */
@CrossOrigin("http://localhost:4200")
@RestController
public class MainController {
    @GetMapping("/car")
    public ArrayList god() {
        final ArrayList arrayList = new Gson().fromJson("['namegod','sdsd','sdsd']", ArrayList.class);
        return arrayList;
    }

    @GetMapping("/onday")
    public String onday() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return
                Functions.getInstance().String_onDayOneHeightOneStation(new FormInfo(new Date(10, 26, 1992)
                        , "WIND_SPEED", "40800", "",
                        "iran__islamic_rep", "12000", 1999, 2017, "m/s"
                ));
    }

}