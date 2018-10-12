package com.amin.ui.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * is created by aMIN on 10/13/2018 at 2:36 AM
 */
@RestController
public class MainController {
    @GetMapping("kir")
    public String god(){
        return new String("kosi");
    }
}
