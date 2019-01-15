package com.amin.config;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * is created by aMIN on 5/31/2018 at 06:58
 */
public class C {
    public static final String PATH_TO_RAW_DIR_ROOT = System.getProperty("user.dir");
    public static final String PATH_TO_RAW_DIR_MONTH_RANDOM = "";
    public static final String OTHER = "";
    public static final String RAW_FMXL_PATH = "/raw_mining_activity.fxml";
    public static final char INSTEAD_PARAM_LESS = '&';
    public static final String STATION_NAME_SIMBOL = "[?^]";
    public static final String SPACE = " ";
    public static final String COUNTRIES_CONFIG_PATH = "config/countries.conf";

    public static final String APP_PROP_PATH = "/application.properties";
    public static final int NowYear = 2017;
    public static final int FIRST_YEAR = 1973;
//    public static String DATA_PATH= "E:\\neuronman\\world";
//    public static String SOCANDARY_DATA_PATH="G:\\new6";
//    public static String THIRDY_PATH="G:\\lastdir";

    public static String DATA_PATH= "";
    public static String SOCANDARY_DATA_PATH="";
    public static String THIRDY_PATH="";
    public static String DATA_PATH_NAME= "data_path";
    public static String SOCANDARY_DATA_PATH_NAME="secondary_data_path";
    public static String THIRDY_PATH_NAME="thirdy_data_path";

    static {
        try {
            DATA_PATH = readPropertieVal(DATA_PATH_NAME);
            System.out.println(DATA_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            SOCANDARY_DATA_PATH = readPropertieVal(SOCANDARY_DATA_PATH_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    static {
        try {
            THIRDY_PATH = readPropertieVal(THIRDY_PATH_NAME);
            System.out.println(C.THIRDY_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static String readPropertieVal(String key) throws IOException, URISyntaxException {


        final String name = "/application.properties";
        final URL resource = C.class.getResource(name);
        final InputStream resourceAsStream = C.class.getResourceAsStream(name);

        File file = new File(resource.toURI().toString());

        if (!file.exists())
            System.err.println("not existed");



//        FileInputStream in = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        resourceAsStream.close();

        String pval = properties.getProperty(key);
        return pval;

    }


    public static void writePropertie(String key, String value) throws IOException, URISyntaxException {


        final URL resource = C.class.getClassLoader().getResource("application.properties");
        File file = new File(resource.toURI());

        if (!file.exists())
            System.err.println("not existed2");

        FileInputStream in = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(in);
        in.close();

        String pval = properties.getProperty(key);
        String toVal = (pval == null || !value.equals(pval)) ? value : pval;


        FileOutputStream out = new FileOutputStream(file);
        properties.setProperty(key, toVal);
        properties.store(out, "god is great");
        out.flush();
        out.close();
        String myValue = (String) properties.getProperty(key);

    }

}


class Load {
    public String getload(String path) {
        String resource = getClass().getResource(path).getPath();
        return resource;
    }
}

