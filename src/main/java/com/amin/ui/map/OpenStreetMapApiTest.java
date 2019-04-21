package com.amin.ui.map;

import com.google.gson.Gson;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.*;

/**
 * is created by aMIN on 10/12/2018 at 10:56 PM
 */
public class OpenStreetMapApiTest {
    public static void main(String[] args) {
        OsmConnection osm = new OsmConnection(
                "https://api.openstreetmap.org/api/0.6/",
                "my user agent", null);
        System.out.println(new Gson().toJson(osm));
        MapDataDao mapDao = new MapDataDao(osm);

        double LONGITUDE_START = 35.6892;
        double LATITUDE_START = 51.3890;


        double LONGITUDE_STOP = 36.261698;
        double LATITUDE_STOP = 51.897801;

        double currentLong = LONGITUDE_START;
        double currentLat = LATITUDE_START;

        MapDataHandler mapDataHandler = new MapDataHandler() {
            public void handle(BoundingBox boundingBox) {
            }

            public void handle(Node node) {
            }

            public void handle(Way way) {
            }

            public void handle(Relation relation) {
                if (relation.getTags().get("type").equals("route")) {
                    System.out.println(relation.getTags().get("name"));
                }
            }
        };

        OsmLatLon min = null;
        OsmLatLon max = null;
        while (currentLat < LATITUDE_STOP) {

            while (currentLong < LONGITUDE_STOP) {
                //System.out.println(currentLat+", "+currentLong);
                min = new OsmLatLon(currentLat, currentLong);
                max = new OsmLatLon(currentLat + 0.01, currentLong + 0.01);
                BoundingBox kazanBuses = new BoundingBox(min, max);
                mapDao.getMap(kazanBuses, mapDataHandler);

                currentLong = currentLong + 0.01;
            }
            currentLong = LONGITUDE_START;
            currentLat = currentLat + 0.01;
        }
    }


    /**
     * This class is fed the map data.
     */
    interface MapDataHandler extends de.westnordost.osmapi.map.handler.MapDataHandler {
        void handle(BoundingBox bounds);

        void handle(Node node);

        void handle(Way way);

        void handle(Relation relation);
    }

}