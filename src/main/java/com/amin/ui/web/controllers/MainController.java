package com.amin.ui.web.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * is created by aMIN on 10/13/2018 at 2:36 AM
 */
@RestController
public class MainController {
    @GetMapping("/car")
    public ArrayList god() {
        final ArrayList arrayList = new Gson().fromJson("['namegod','sdsd','sdsd']", ArrayList.class);
        return arrayList;
    }

}