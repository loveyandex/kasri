package com.amin.database.database;

import com.amin.pojos.LatLon;
import com.amin.pojos.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
//import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DatabaseHandler {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());

    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:dbderby;create=true";
    private static final String DB_URL2 = "jdbc:derby:dbderby3;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    static {
//        createConnection();
        createConnection(DB_URL2);
        inflateDB();
    }

    private DatabaseHandler() {
//        org.apache.derby.jdbc.EmbeddedDriver driver = new EmbeddedDriver();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    private static void inflateDB() {
        List<String> tableData = new ArrayList<>();
        try {
            Set<String> loadedTables = getDBTables();
            System.out.println("Already loaded tables " + loadedTables);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(DatabaseHandler.class.getResourceAsStream("/database/tables.xml"));
            NodeList nList = doc.getElementsByTagName("table-entry");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element entry = (Element) nNode;
                String tableName = entry.getAttribute("name");
                String query = entry.getAttribute("query");
                if (!loadedTables.contains(tableName.toLowerCase())) {
                    tableData.add( query);
                }
            }
            if (tableData.isEmpty()) {
                System.out.println("Tables are already loaded");
            }
            else {
                System.out.println("Inflating new tables.");
                createTables(tableData);
            }
        }
        catch (Exception ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("after class forname");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("after db_url connection");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    private static void createConnection(String dburl) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            System.out.println("after class forname");
            conn = DriverManager.getConnection(dburl);
            System.out.println("after db_url connection");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static Set<String> getDBTables() throws SQLException {
        Set<String> set = new HashSet<>();
        DatabaseMetaData dbmeta = conn.getMetaData();
        readDBTable(set, dbmeta, "TABLE", null);
        return set;
    }

    private static void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String searchCriteria, String schema) throws SQLException {
        ResultSet rs = dbmeta.getTables(null, schema, null, new String[]{searchCriteria});
        while (rs.next()) {
            set.add(rs.getString("TABLE_NAME").toLowerCase());
        }
    }




    public static ResultSet execQuery(String query) {
        ResultSet result;
        try {
            if (stmt == null)
                stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }


    public static void main(String[] args) throws Exception {
        final Station station = new Station().setStationNumber("23232").setCountry("godconute").setCityName("ko").setLatLong(new LatLon(23.23, -83.23*Math.random()));
//        DatabaseHandler.getInstance().insertStations(station);

        final ObservableList<PieChart.Data> getstations = DatabaseHandler.getInstance().getstations();

        getstations.forEach(data -> System.out.println(data));


    }

    public ObservableList<PieChart.Data> getstations() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = "SELECT COUNT(*) FROM stations";
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total stations (" + count + ")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public void insertStations(Station station) throws SQLException {

        final PreparedStatement preparedStatement = conn.prepareStatement("insert into stations (station,country, citiname, lati ,longi) values (?,?,?,?,?)");

        preparedStatement.setString(1, station.getStationNumber());
        preparedStatement.setString(2, station.getCountry());
        preparedStatement.setString(3,station.getCityName());
        preparedStatement.setString(4, String.valueOf(station.getLatLong().getLat()));
        preparedStatement.setString(5, String.valueOf(station.getLatLong().getLogn()));
        preparedStatement.executeUpdate();
        System.err.println("good added");

    }

    private static void createTables(List<String> tableData) throws SQLException {
        Statement statement = conn.createStatement();
        statement.closeOnCompletion();
        for (String command : tableData) {
            System.out.println(command);
            statement.addBatch(command);
        }
        statement.executeBatch();
    }

    public Connection getConnection() {
        return conn;
    }
}
