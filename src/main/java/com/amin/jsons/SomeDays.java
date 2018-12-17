package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * is created by aMIN on 12/17/2018 at 11:21 PM
 */

@Getter
@Setter
@Accessors(chain = true)
public class SomeDays {
    private Date fromDate;
    private Date toDate;
    private String feaureName;
    private String StationNumber;
    private String StationName;
    private String Country;
    private String height;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;


}
