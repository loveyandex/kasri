package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class Response {
    Map<String, City> descriptor;
    // standard getters & setters...
}
