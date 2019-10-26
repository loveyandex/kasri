package com.amin.ui.web;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * created By gOD on 9/26/2019 7:11 PM
 */

@Getter
@Setter
@Accessors(chain = true)
public class OndayResponseEntity {
    private String name;
    private double value1;
    private double value2=9000;
    private String unit;
}
