package com.amin.jsons;

import lombok.Getter;

/**
 * is created by aMIN on 10/6/2018 at 3:02 AM
 */
@Getter
public enum TEMP {
    Celsius("celsius"),
    Kelvin("kelvin"),
    Fahrenheit("fahrenheit"),
    Rankine("rankine"),

    ;

    private final String name;

    TEMP(String name) {
        this.name = name;
    }
}
