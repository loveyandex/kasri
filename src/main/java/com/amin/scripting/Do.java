package com.amin.scripting;

import java.io.IOException;
import java.util.ArrayList;

public interface Do {
    void filterAndDo();
    ArrayList<ArrayList<Double>> filterAndDo(String dataFilePath, int... cols) throws IOException;
}