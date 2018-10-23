package org.amin.jsons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * is created by aMIN on 10/23/2018 at 1:09 AM
 */
@Getter
@Setter
@Accessors(chain = true)
public class TestPojo {
    String name;
    String age;
    double avg;
}
