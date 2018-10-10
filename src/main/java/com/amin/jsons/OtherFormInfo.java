package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * is created by aMIN on 6/6/2018 at 20:17
 */
@Getter
@Setter
@Accessors(chain = true)
public class OtherFormInfo {
    public Date Date;
    private String feaureName;
    public ArrayList<String> StationNumbersList;
    public ArrayList<String> StationNamesList;
    public String Country;
    private String height;
    private String lowHeightRange;
    private String highHeightRange;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;
    private String dirTOSave;
    private String childFileName;

}

